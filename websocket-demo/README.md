> 使用`SpringBoot`整合`WebSocket`记录。

1. 映入依赖 `pom`
   ```xml
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <version>${spring.boot.version}</version>
      <exclusions>
          <exclusion>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-tomcat</artifactId>
          </exclusion>
      </exclusions>
   </dependency>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-undertow</artifactId>
      <version>${spring.boot.version}</version>
   </dependency>
   <!--websocket-->
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-websocket</artifactId>
      <version>${spring.boot.version}</version>
   </dependency>
   <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.24</version>
   </dependency>
   ```

2. 创建 `WebSocketConfig`
   ```java
   @Configuration
   public class WebSocketConfig {
   
       @Bean
       public ServerEndpointExporter serverEndpointExporter(){
           return new ServerEndpointExporter();
       }
   }
   ```

3. 创建 `WebSocketServer`

   ```java
   @Slf4j
   @Component
   @ServerEndpoint("/websocket/{id}")
   public class WebSocketServer {
   /**
   * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
   */
   private static int ONLINE_COUNT = 0;
   
       /**
        * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
        */
       private static CopyOnWriteArraySet<WebSocketServer> WEB_SOCKET_SET = new CopyOnWriteArraySet<>();
   
       /**
        * 与某个客户端的连接会话，需要通过它来给客户端发送数据
        */
       private Session session;
   
       private String id = "";
   
       @OnOpen
       public void onOpen(Session session, @PathParam("id") String id) {
           this.session = session;
           WEB_SOCKET_SET.add(this);
           addOnlineCount();
           log.info("有新连接加入！当前在线人数为" + getOnlineCount());
           this.id = id;
           try {
               sendMessage("连接成功");
           } catch (IOException e) {
               log.error("websocket IO异常");
           }
       }
   
       @OnClose
       public void onClose() {
           WEB_SOCKET_SET.remove(this);
           subOnlineCount();
           log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
       }
   
       @OnMessage
       public void onMessage(String message, Session session) {
           log.info("来自客户端的消息:" + message);
           for (WebSocketServer server : WEB_SOCKET_SET) {
               try {
                   server.sendMessage(message);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       }
   
       @OnError
       public void onError(Session session, Throwable throwable) {
           log.error("发生错误");
           throwable.printStackTrace();
       }
   
       /**
        * 实现服务器主动推送
        *
        * @param message
        * @throws IOException
        */
       public void sendMessage(String message) throws IOException {
           this.session.getBasicRemote().sendText(message);
       }
   
       /**
        * 群发自定义消息
        *
        * @param message
        * @param id
        */
       public static void sendInfo(String message, @PathParam("id") String id) {
           log.info("推送消息到窗口" + id + "，推送内容:" + message);
           for (WebSocketServer server : WEB_SOCKET_SET) {
               try {
                   if (id == null) {
                       server.sendMessage(message);
                   } else if (server.id.equals(id)) {
                       server.sendMessage(message);
                   }
               }catch (IOException e){
                   continue;
               }
           }
       }
   
       private static synchronized int getOnlineCount() {
           return ONLINE_COUNT;
       }
   
       private static synchronized void addOnlineCount() {
           ONLINE_COUNT++;
       }
   
       private static synchronized void subOnlineCount() {
           ONLINE_COUNT--;
       }
   
       @Override
       public int hashCode() {
           return Objects.hash(session, id);
       }
   
       @Override
       public boolean equals(Object obj) {
           if (this == obj) {
               return true;
           }
           if (obj == null || getClass() != obj.getClass()) {
               return false;
           }
           WebSocketServer that = (WebSocketServer) obj;
           return Objects.equals(session, that.session) && Objects.equals(id, that.id);
       }
   }
   ```
   
4. 创建测试页面 `test.html`
   ```html
   <!DOCTYPE HTML>
   <html>
   <head>
       <title>My WebSocket</title>
   </head>
   <meta charset="UTF-8" />
   <body>
   <div>
       <input type="text" id="content" placeholder="请输入...">
   </div>
   <button type="submit" onclick="submit()">发送</button>
   <div id="message"></div>
   </body>
   
   <script type="text/javascript">
       let websocket = null;
   
       //判断当前浏览器是否支持WebSocket
       if ('WebSocket' in window) {
           websocket = new WebSocket("ws://localhost:8080/websocket/test01");
       } else {
           alert('Not support websocket')
       }
   
       //连接发生错误的回调方法
       websocket.onerror = function () {
           setMessageInnerHTML("error");
       };
   
       //连接成功建立的回调方法
   
       websocket.onopen = function (event) {
           setMessageInnerHTML("open");
       }
   
       //接收到消息的回调方法
       websocket.onmessage = function (event) {
           let data = event.data
           console.log(data);
           setMessageInnerHTML(event.data);
       }
   
       //连接关闭的回调方法
       websocket.onclose = function () {
           setMessageInnerHTML("close");
       }
   
       //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
       window.onbeforeunload = function () {
           websocket.close();
       }
   
       //将消息显示在网页上
       function setMessageInnerHTML(innerHTML) {
           document.getElementById('message').innerHTML += innerHTML + '<br/>';
       }
   
       //关闭连接
       function closeWebSocket() {
           websocket.close();
       }
   
       function submit() {
           let value = document.getElementById('content').value;
           websocket.send(value);
           return false;
       }
   </script>
   </html>
   ```
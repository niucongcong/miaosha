# miaosha
前端我们使用的都是Vue框架，我们把我们的代码拉到本地或者直接下来我们的代码压缩包，在我们本地的代码区进行解压

我的系统为window10
node version为11.15.0
npm version为6.14.8
vue version为2.9.6


后台管理系统和前端秒杀系统配置和运行：
想运行Vue代码，我们需要本地存在nodeJs环境，相关环境配置可参考链接https://www.runoob.com/nodejs/nodejs-install-setup.html
然后我们本地就会存在npm这一命令，
配置vue环境，请参考https://www.cnblogs.com/weiwei-python/p/9754384.html
然后我们打开我们的项目根目录，先执行npm install然后运行 npm run dev，然后在命令行会提示我们代码创建的本地端口
比如127.0.0.1:8080，然后我们就可以在浏览器中打开我们的前端项目了


我们的后端部署步骤如下：
首先需要安装java环境，请参考https://www.runoob.com/java/java-environment-setup.html
安装redis和mysql，我们的redis采用的是o数据库 没有密码 如果您需要设置密码 请在java项目的application.properities中修改spring.redis.*参数
mysql数据库我们数据库为miaosha，spring.datasource.url: jdbc:mysql://127.0.0.1:3306/miaosha?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8
关键点！！！：我们需要本地安装rocketMq环境，我们需要配置的是4.3.0版本
请参考 https://blog.csdn.net/bbc2005/article/details/85218497 进行mq的打开，不然秒杀系统无法连接本地rocketMQ。
我们后端代码是java代码 采用maven创建程序。


秒杀系统的测试：
我们需要先运行我们的后端代码，不然我们前端没有数据展示
我们的秒杀系统中，我们首先需要登录，秒杀系统的测试账号密码为15620285161 15620285161         后台管理系统的账号密码为admin admin 或者assistant assistant
秒杀系统我们点击任何一个商品进入商品详情页，然后点击下单，这个时候会提示未登录或者商品未发布，这个是正常提示，以为着我们需要在后台管理系统中先进行商品的发布
我们登陆我们的后台管理系统然后进入发布商品页面 进行商品的发布 然后重回秒杀系统页面进行对应商品的下单  加入购物车功能不需要商品发布




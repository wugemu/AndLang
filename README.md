# AndLang框架V1.0
方便Android开发者快速搭建项目框架
# 目的
开发过程中，经常View与Data混杂在一起，项目复杂化，代码量增加时，逻辑难以阅读，影响迭代开发；AndLang目的是将View与Data进行绑定，当开发者操作Data时，框架能够同时刷新该Data绑定的View，这样开发者能够将更多的精力用在业务逻辑处理，提高开发效率，代码逻辑清晰。同时整合常用开发包到AndLang框架中，方便Android开发者快速搭建项目框架。
# 原理
运用类的反射将注册的Activity中的所有定义View(目前 TextView EditText ListView ImageView)参数字段添加到Map<KEY，VIEW>中，KEY是字段名称，VIEW是该字段指向view；当设置Data中KEY的值时，同时取出KEY所对应的VIEW进行赋值。
# 弊端
对于Activity 和 Fragment 的View字段命名 和 ViewModel中字段命名 需要保持一致；
# 已整合开发包
butterknife (注入包)，glide(图片加载库，支持gif图)，okhttp(目前流行的网络请求库)，gson(gson数据处理库,使用新版本2.8.2)
# 已整合的方法
Adapter和ViewHolder优化。
# 模块及任务
一个Activity或Fragment，一个Presenter， 一个ViewModel。
Activity 和 Fragment 负责设置布局，View注入，事件注册，初始化Presenter，特殊View处理。
Presenter 负责创建ViewModel实例，响应事件处理，控制ViewModel字段间接控制View，网络数据请求，网络请求结果处理。
ViewModel 负责将Activity或Fragment中的View字段绑定到ViewModel字段，被赋值时可同时刷新View。
# 使用方法
1.通过添加lib工程方式引入到项目中；

2.在项目的根gradle中添加
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'
ext{
		isBuildModule=false; 
}

3.使用butterknife注入时，需要在每个module中的gradle中进行配置引入；

4.创建Application,初始化设置框架的图片加载库；

5.创建Model类继承BaseLangViewModel<和Model类名一致>，重写updateModel方法，updateModel方法中将参数model中的值赋值给当前model；

6.创建Presenter类继承BaseLangPresenter<对应Model类名>；

7.创建的Activity 继承 BaseLangActivity<对应Presenter类名>；

# 后续。。。

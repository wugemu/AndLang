# AndLang框架V1.0
方便Android开发者快速搭建项目框架
# 目的
开发过程中，经常View与Data混杂在一起，项目复杂化，代码量增加时，逻辑难以阅读，影响迭代开发；AndLang目的是将View与Data进行绑定，当开发者操作Data时，框架能够同时刷新该Data绑定的View，这样开发者能够将更多的精力用在业务逻辑处理，提高开发效率，代码逻辑清晰。同时整合常用开发包到AndLang框架中，方便Android开发者快速搭建项目框架。
# 原理
方式1.运用类的反射将注册的Activity中的所有定义View(目前 TextView EditText ListView ImageView)参数字段添加到Map<KEY，VIEW>中，KEY是字段名称，VIEW是该字段指向view；当设置Data中KEY的值时，同时取出KEY所对应的VIEW进行赋值。

方式2（推荐).运用java中的kvo模式(observer和observable),对model数据变化进行监控，控制view显示。
# 弊端
方式1.对于Activity 和 Fragment 的View字段命名 和 ViewModel中字段命名 需要保持一致，以及未知问题；
# 已整合开发包
butterknife (注入包)，glide(图片加载库，支持gif图)，okhttp(目前流行的网络请求库)，gson(gson数据处理库,使用新版本2.8.2)
# 已整合的方法
Adapter和ViewHolder优化。
# 模块及任务
一个Activity或Fragment，一个Presenter， 一个ViewModel。

Activity 和 Fragment 负责设置布局，View注入，事件处理，初始化Presenter，特殊View处理。

Presenter 负责创建ViewModel实例，控制ViewModel字段间接控制View，网络数据请求，网络请求结果处理。

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

# 类说明：
# BaseLangViewModel
方法名				说明

setValue(key,value)		设置字段值同时设置view的值(TextView.text,EditText.text,ImageView.src)

getValueFromKey(key)		通过字段key获取值

bindView(key)			设置view的值(TextView.text,EditText.text,ImageView.src)

getValueFromView(key)		获取View的值(TextView.text,EditText.text)同时设置字段值

registView(key,view)		注册view绑定model

registView(activity)		注册Activity的view到model

registView(fragment)		注册Fragment的view到model

setListValue(key,value,adapter)	设置List 类型字段值同时设置listview的数据源

bindListView(key,adapter)	设置listview的数据源

isSupportView(fieldclass)	判断某个字段是否属于支持的View类型

notifyView			刷新所有属于支持的View类型的数据(不包含listview)

updateModel(model)		子类中必须实现此方法，设置刷新默认model中的字段值

# BaseLangPresenter
方法名						说明

BaseLangPresenter(activity,modelclass)		构造函数，创建BaseLangViewModel子类对象，绑定activity中的View到model

BaseLangPresenter(fragment,activity,modelclass)	构造函数，创建BaseLangViewModel子类对象，绑定fragment中的View到model

registView(key,view)				单个注册View到Model

initModel()					BaseLangPresenter的子类必须实现此方法，进行数据初始化，网络请求等操作

# 后续。。。

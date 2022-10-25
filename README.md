# DevTools-plugin

## 简介
用于快捷执行日常开发所需的命令，命令可以自由编辑。


## 使用
1、安装Alias脚本工具插件  DevTools-plugin.jar\n
2、Tools→DevTools→查看配置文件路径并自定义自己的命令\n
3、重新打开工具界面，单击执行命令\n

## 配置文件例子
```json
{
    "data":[
        {
            "key":"执行Monkey",
            "value":"adb shell monkey -p com.hiyaludo.android"
        },
        {
            "key":"XXX",
            "value":"xxx"
        }
    ]
}
```

![image](https://user-images.githubusercontent.com/19563459/197697035-8d210bc0-a9e6-47ad-8cca-6c388dcf6846.png)

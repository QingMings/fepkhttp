#  数据发布服务说明
##  一、 dsv-all 模块是数据发布服务主要逻辑实现

#### 1.服务启动方式
`java -jar  dsv-all-1.0.0-execute.jar -conf  ./conf/configLocal.json`

##### 多核提高性能  -instances 10  启动十个http实例，其中 instances 数量不超过cpu核心数
`java -jar  dsv-all-1.0.0-execute.jar  -instances 10 -conf  ./conf/configLocal.json`

### 2.数据发布服务地址说明
`端口和数据配置查看 conf/configLocal.json`
其中底图服务提供全球18级影像数据，中文标签提供全球12级数据，地形数据只有8级，且效果一般，不建议使用。
```
底图服务地址：http://ip:port/api/readImage?col={x}&row={y}&lev={z}
中文标注服务地址：http://ip:port/api/readLabel?col={x}&row={y}&lev={z}
地形：http://ip:port/api/readDem?col={x}&row={y}&lev={z}
```
`eg.  http://localhost:11111/api/readImage?col=0&row=1&lev=1`



## 二、授权流程说明
1.在客户机分发运行 dsv-all 服务[dsv-all-1.0.0-execute.jar]，并获取申请码（申请码中包含了目标机器的cpu序列号，主板序列号，mac地址，网卡ip等信息）
```bash
# 使用curl访问 获取申请码地址，获取申请码并写入到 appcode.txt
curl --location 'http://127.0.0.1:9000/app/getCode' >>appcode.txt
```
2.将获得到的申请码 发送到公司机器，在公司机器上用生成授权机器生成授权码，并发送给客户机，放在程序配置文件夹下即可，无需重启程序。
```bash
# 1.启动授权码生成服务
java -jar  license-gen-1.0.0-execute.jar
# 2. 访问生成 授权码

curl --location 'http://localhost:11112/api/license/generate' \
--header 'Content-Type: application/json' \
--data '  {
      "keyPwd": "123456a123456",
      "storePwd": "123456abc",
      "privateKeysStorePath": "D:\\code\\fepkhttp\\license-gen\\src\\main\\resources\\privateKeys.keystore",
      "consumerAmount": 1,
      "expiryTime": "2024-07-23 14:05:00",
      "appCode": "5b2d533e14522c70d9580384a2b7e1303658be06423039ac29aade36f3acf6138193130ae5a327d15ae8557ebe3b5598b9b407286e6fd333775b2fa22217295122f3e456db549980e5c9c6f6c6370d924a71aca8821fa13fc3d451c1c9f657fc63e278edfc30ba2d964ee5211be28ee483cb67230fcbfe7f004e0a9c6724fd26e8f73b5026463e87769d90a01fce424cab8297a43270a98b5df4878110e61992"
  }'
# 其中，appCode是授权码，替换成新生成的，expiryTime 是 过期时间，根据情况设置即可，
```


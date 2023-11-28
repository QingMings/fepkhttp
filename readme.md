#  dsv 服务
### 简单说明
#### 1.服务启动方式
`java -jar  dsv-1.0.0-all.jar -conf  ./conf/config.json`

多核提高性能
`java -jar  dsv-1.0.0-all.jar  -instances 10 -conf  ./conf/config.json`

### 2.服务说明
`端口和数据配置查看 conf/config.json`
```
底图服务地址：http://ip:port/api/readImage?col={x}&row={y}&lev={z}
中文标注服务地址：http://ip:port/api/readLabel?col={x}&row={y}&lev={z}
地形：http://ip:port/api/readDem?col={x}&row={y}&lev={z}
```
`eg.  http://localhost:11111/api/readImage?col=0&row=1&lev=1`

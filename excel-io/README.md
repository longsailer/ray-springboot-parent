EXCEL-IO 是一个基于POI进行封装的工具包，它主要提供在开发过程经常需要导入/导出数据为Excel时，而使用的功能。对POI封装后，简化类的调用，使开发者在调用，只需要考虑一些简单的几个前提条件，即可实现导出功能。

#### 1. 版本历史
目前最新版本是1.0.0-SNAPSHOT（待经真实系统验证后，会发布RELEASE版本）

1.0.0-SNAPSHOT： 数据导出为Excel，写入到 HttpServletResponse 的 OutputStream
#### 2. 主类介绍
先提供的使用频率最高的，在页面的导出功能。在项目中，可以直接引入类`ExcelExportHttpResponse`，分别设置要导出的文件名、表头、实体转换规则之后，直接调其write方法，即可实现导出。可以参考framework-demo框架代码中，framework-admin中的`TestExportController`类，如下：
```java
List<Enterprises> data = enterprisesService.queryList(ep); //查询出的数据
ExcelExportHttpResponse<Enterprises> eer = new ExcelExportHttpResponse<Enterprises>(); //实例化导出工具类
eer.setResponse(response); //设置response，因为是通过response写出，发起浏览器下载
eer.setFileName("测试.xls"); //设置导出的文件名
eer.setHeader(new String[]{"ID","名称"}); //设置表头

//把实体类转化为数组
eer.setExcelCellSetter(new ExcelCellSetter<Enterprises>() {
	public String[] mapping(Enterprises t) {
		return new String[]{
			t.getId(), t.getEpName()
		};
	}
});
//导出数据
eer.write(data);
```

#### 3. Maven引用
```xml
<!-- 项目parent引用下面这个 -->
<parent>
	<groupId>com.cesgroup</groupId>
	<artifactId>zn-springboot-parent</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</parent>

<!-- excel导入导出包 -->
<dependency>
	<groupId>com.cesgroup</groupId>
	<artifactId>excel-io</artifactId>
</dependency>
```

#### 4. 结语
通过后面不断的使用，会有更多更丰富的类出现，也期待大家贡献自己的想法。
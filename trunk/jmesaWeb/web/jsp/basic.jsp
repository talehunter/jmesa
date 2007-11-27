<html>

<head>
	<title>Basic JMesa Example</title>

<script type="text/javascript">
	$(document).ready(function() {
	   addDropShadow('${pageContext.request.contextPath}/images/table/');
	});
</script>
	
</head>

<body>

	<p class="content">
		The basic JMesa example to build a table. Also using the State feature. You can see that
		everytime you come back to this page the table is sorted, filted, and paged the way
		that you left it.
	</p>
	
	<p class="content">
		Other examples:<br/>
		<a href="${pageContext.request.contextPath}/limit.run?restore=true">Limit (with AJAX)</a> <br/>
		<a href="${pageContext.request.contextPath}/groovy.run?restore=true">Groovy</a><br/>
		<a href="${pageContext.request.contextPath}/tag.run?restore=true">Tag</a><br/>
	</p>
	
	<form name="presidentsForm" action="${pageContext.request.contextPath}/basic.run">
	   ${presidents}
	</form>
	
	<p class="content">
		This example source code can be found 
		<a href="http://code.google.com/p/jmesa/wiki/FacadeExample">here</a>.
	</p>
	
	
<script type="text/javascript">
function onInvokeAction(id) {
	setExportToLimit(id, '');
	createHiddenInputFieldsForLimitAndSubmit(id);
}
function onInvokeExportAction(id) {
	var parameterString = createParameterStringForLimit(id);
	location.href = '${pageContext.request.contextPath}/presidents.run?' + parameterString;
}
</script>

</body>

</html>

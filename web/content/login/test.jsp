<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html;CHARSET=iso-8859-1"/>
		<title>Login to ITR</title>
		<link REL="stylesheet" TYPE="text/css" HREF="include/ITR.CSS"/>
		<script type="text/javascript">
			<%@ include file="../../helpers/en_validate.js" %>
			function display(str)  {
				alert("HEJ: "+str);
			}

			function validateAdd(theForm)  {
				if ( !isNumeric(theForm.afield, "IT WORKS") ) {
					return false;
				}
			}
			
		</script>
	</head>
	<body>
		<form method="post" action="changeProjectMembersResult.jsp" name="projectmemberinfo" onsubmit="return validateAdd(this)" enctype="text/plain">
			<input type="text" name="afield" value="Testing" />
			<input type="button" name="test1" value=" Test1 " onclick="javascript:display(document.projectmemberinfo.afield.value);" />
			<input type="button" name="test2" value=" Test2 " onclick="javascript:display(document.projectmemberinfo.afield.value);" />
			<input type="button" name="test3" value=" Test3 " onclick="javascript:display(afield.value);" />
			<input type="submit" name="submit" value="Submitting" />
		</form>
	</body>
</html>
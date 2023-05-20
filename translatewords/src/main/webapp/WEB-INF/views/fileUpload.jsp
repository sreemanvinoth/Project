<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>File Uploading</title>
<style type="text/css">
/* fieldset {
	display: block;
	margin-inline-start: 2px;
	margin-inline-end: 2px;
	padding-block-start: 0.35em;
	padding-inline-start: 0.75em;
	padding-inline-end: 0.75em;
	padding-block-end: 0.625em;
	min-inline-size: min-content;
	border-width: 2px;
	border-style: groove;
	border-color: rgb(192, 192, 192);
	border-image: initial;
} */
fieldset {
	border: 2px rgb(240, 36, 36) solid;
	width: fit-content;
}
</style>
</head>
<body>
	<center>
		<fieldset>
			<h3>Select a File to Upload</h3>
			<form action="upload" enctype="multipart/form-data" , method="post">

				<h4>
					<label>File to Upload : </label>
				</h4>
				<br> <input type="file" name="file" size="50" /> <br> <br>
				<input type="submit" value="Upload">

			</form>
	</center>
	</fieldset>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/sign-up.css">
<title>SignUp</title>
</head>
<body>
	<div class="containr">
	<h2>SignUP</h2>
	<div class="form-container">
		<f:form action="/validate" modelAttribute="user">
			<label for="user-name">Name </label>
			<br>
			<f:errors path="name"></f:errors>
			<f:input path="name" id="user-name" />
			<br>
			<label for="user-email">Email </label>
			
			<br>
			<f:errors path="email"></f:errors>
			<f:input path="email" id="user-email" />
			<br>
			<label for="user-pass">Password </label>
			<br>
			<f:errors path="password"></f:errors>
			<f:input path="password" id="user-pass" type="password"/>
			<br>
			<label for="user-city">City </label>
			<br>
			<f:errors path="address.city"></f:errors>
			<f:input path="address.city" id="user-city" />
			<br>
			<label for="user-district">District </label>
			<br>
			<f:errors path="address.district"></f:errors>
			<f:input path="address.district" id="user-district" />
			<br>
			<label for="user-street">Street </label>
			<br>
			<f:errors path="address.street"></f:errors>
			<f:input path="address.street" id="user-street" />
			<br>
			<label for="user-zCode">Zip Code </label>
			<br>
			<f:errors path="address.zipCode"></f:errors>
			<f:input path="address.zipCode" id="user-zCode" />
			<br>
			<input type="submit" value="Save">
		</f:form>
	</div>
	</div>
</body>
</html>
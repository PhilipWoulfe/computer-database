<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List"%>
<%@ page import="com.excilys.formation.dto.CompanyDto"%>
<%@ page import="com.excilys.formation.dto.ComputerDto"%>
<%@ page import="java.util.Map"%>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<c:if test="${success}">
						<p style="color:red"> Ordinateur ajouté</p>
					</c:if>
					<form id="addForm" action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName"
									<c:if test="${not empty computerDto.name}">value="${computerDto.name}"</c:if>
									placeholder="Computer Name">
									<c:if test="${not empty errors['name']}"><span style="color:red">${errors["name"]}</span></c:if>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									<c:if test="${not empty computerDto.introduced}">value="${computerDto.introduced}"</c:if>
									name="introduced" placeholder="Computer Introduced">
									<c:if test="${not empty errors['introduced']}"><span style="color:red">${errors["introduced"]}</span></c:if>
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									<c:if test="${not empty computerDto.discontinued}">value="${computerDto.discontinued}"</c:if>
									name="discontinued" placeholder="Computer Discontinued">
									<c:if test="${not empty errors['discontinued']}"><span style="color:red">${errors["discontinued"]}</span></c:if>
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach items="${listCompanies}" var="companyDto">
										<option value="${companyDto.id}">${companyDto.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.validate.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/addComputer.js"></script>
</body>
</html>
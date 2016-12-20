<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/jquery-ui.min.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${computerDto.id}</div>
					<h1>Edit Computer</h1>
					<c:if test="${success}">
						<p style="color:red"> Ordinateur edité</p>
					</c:if>
					<sf:form id="editComputer" action="" method="POST" modelAttribute="computerDto">
						<input type="hidden" id="computerId" name="computerId" <c:if test="${not empty computerDto.id}">value="${computerDto.id}"</c:if>/>
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<sf:label path="name">Computer name</sf:label> 
								<sf:input type="text" class="form-control" id="computerName" path="name" placeholder="Computer Name"/>
								<sf:errors path="name" cssClass="alert alert-danger" element="div" />
							</div>
							<div class="form-group">
								<sf:label path="introduced">Introduced date</sf:label> 
								<sf:input type="date" class="form-control" id="introduced"	path="introduced" placeholder="Computer Introduced"/>
								<sf:errors path="introduced" cssClass="alert alert-danger" element="div" />
							</div>
							<div class="form-group">
								<sf:label path="discontinued">Discontinued date</sf:label> 
								<sf:input type="date" class="form-control" id="discontinued" path="discontinued" placeholder="Computer Discontinued"/>
								<sf:errors path="discontinued" cssClass="alert alert-danger" element="div" />
							</div>
							<div class="form-group">
								<sf:label path="companyId">Company</sf:label> 
								<sf:select	class="form-control" id="companyId" path="companyId">
									<c:forEach items="${listCompanies}" var="companyDto">
										<sf:option value="${companyDto.id}">${companyDto.name}</sf:option>
									</c:forEach>
								</sf:select>
								<sf:errors path="companyId" cssClass="alert alert-danger" element="div" />
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</section>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/jquery.validate.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script src="../js/editComputer.js"></script>
</body>
</html>
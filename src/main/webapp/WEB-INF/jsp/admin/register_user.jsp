<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<!-- REGISTER FORM -->
<div class="container text-white">
		<div class="py-5 text-center">
			<h2><fmt:message key="user_registration.header"/></h2>
		</div>
		
			<div class="mx-5">
				<form action="controller?action=registerUser" method="post">
					<div class="row g-3">
						<div class="col-sm-6">
							<label for="firstName" class="form-label"><fmt:message key="user_registration.first_name"/></label> 
							<input
								type="text" class="form-control" id="firstName" name="firstName" placeholder="<fmt:message key="user_registration.first_name"/>"
								value="${requestScope.userForm.firstName}" required>
							
						</div>

						<div class="col-sm-6">
							<label for="lastName" class="form-label"><fmt:message key="user_registration.last_name"/></label> <input
								type="text" class="form-control" id="lastName" name="lastName" placeholder="<fmt:message key="user_registration.last_name"/>"
								value="${requestScope.userForm.lastName}" required>
						</div>
						<c:if test="${not empty requestScope.userAlreadyExists}">
							<div class="alert alert-danger align-right" role="alert">
  									${requestScope.userAlreadyExists}
			</div>
						</c:if>
						<div class="col-12">
							<label for="login" class="form-label"><fmt:message key="user_registration.login"/></label>
							<div class="input-group has-validation">
								<input type="text"
									class="form-control" id="login" name="login" placeholder="<fmt:message key="user_registration.login"/>" value = "${requestScope.userForm.login}"
									required>
							</div>
						</div>

						<div class="col-12">
							<label for="email" class="form-label"><fmt:message key="user_registration.email"/></label> <input type="email"
								class="form-control" id="email" name="email" placeholder="you@example.com" value = "${requestScope.userForm.email}">
						</div>
						
						<div class="col-12">
							<label for="city" class="form-label"><fmt:message key="user_registration.city"/> </label> <input type="text"
								class="form-control" id="city" name="city" placeholder="<fmt:message key="user_registration.city_placeholder"/>" value = "${requestScope.userForm.city}">
						
						</div>

						<div class="col-12">
							<label for="address" class="form-label"><fmt:message key="user_registration.address"/></label> <input
								type="text" class="form-control" id="address"
								placeholder="<fmt:message key="user_registration.address_placeholder"/>" name="address" value = "${requestScope.userForm.address}" required>
						</div>				
					</div>
					<hr class="my-4">

					<button class="w-80 btn btn-warning btn-lg" type="submit">Register</button>
				</form>
			</div>


</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
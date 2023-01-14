<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<!-- REGISTER FORM -->
<div class="container text-white">
		<div class="py-5 text-center">
			<h2>User registration form</h2>
		</div>
		
			<div class="mx-5">
				<form action="controller?action=registerUser" method="post">
					<div class="row g-3">
						<div class="col-sm-6">
							<label for="firstName" class="form-label">First name</label> 
							<input
								type="text" class="form-control" id="firstName" name="firstName" placeholder=""
								value="${requestScope.userForm.firstName}" required>
							
						</div>

						<div class="col-sm-6">
							<label for="lastName" class="form-label">Last name</label> <input
								type="text" class="form-control" id="lastName" name="lastName" placeholder=""
								value="${requestScope.userForm.lastName}" required>
						</div>
						<c:if test="${not empty requestScope.userAlreadyExists}">
							<div class="alert alert-danger align-right" role="alert">
  									${requestScope.userAlreadyExists}
			</div>
						</c:if>
						<div class="col-12">
							<label for="login" class="form-label">Login</label>
							<div class="input-group has-validation">
								<input type="text"
									class="form-control" id="login" name="login" placeholder="Login" value = "${requestScope.userForm.login}"
									required>
							</div>
						</div>

						<div class="col-12">
							<label for="email" class="form-label">Email</label> <input type="email"
								class="form-control" id="email" name="email" placeholder="you@example.com" value = "${requestScope.userForm.email}">
						</div>
						
						<div class="col-12">
							<label for="city" class="form-label">City </label> <input type="text"
								class="form-control" id="city" name="city" placeholder="Kiyv" value = "${requestScope.userForm.city}">
						
						</div>

						<div class="col-12">
							<label for="address" class="form-label">Address</label> <input
								type="text" class="form-control" id="address"
								placeholder="1234 Main St" name="address" value = "${requestScope.userForm.address}" required>
						</div>				
					</div>
					<hr class="my-4">

					<button class="w-80 btn btn-warning btn-lg" type="submit">Register</button>
				</form>
			</div>


</div>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
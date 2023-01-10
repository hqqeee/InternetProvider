<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ include file="/WEB-INF/jsp/subscriber/profile_header.jspf"%>
<div class="text-white  text-start">
<div class="d-flex justify-content-between align-items-center">
	<p class="h2 m-3 fw-bold">Information</p>
	</div>
	<hr class="style1">
	
	<div class="row">
		<div class="col-md-6">
			<label class="text-warning fw-semibold ">Login</label>
		</div>
		<div class="col-md-6">
			<p>
				<c:out value="${currentUser.login}" />
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 text-warning fw-semibold">
			<label>Name</label>
		</div>
		<div class="col-md-6">
			<p>
				<c:out value="${currentUser.firstName}" />
				<c:out value="${currentUser.lastName}" />
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 text-warning fw-semibold">
			<label>Email</label>
		</div>
		<div class="col-md-6">
			<p>
				<c:out value="${currentUser.email}" />
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 text-warning fw-semibold">
			<label>City</label>
		</div>
		<div class="col-md-6">
			<p>
				<c:out value="${currentUser.city}" />
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6 text-warning fw-semibold">
			<label>Address</label>
		</div>
		<div class="col-md-6">
			<p>
				<c:out value="${currentUser.address}" />
			</p>
		</div>
	</div>

</div>
<c:if test="${sessionScope.loggedUser.roleId == 2}">
	<div class="row justify-content-md-center container-fluid">
		<div class="align-items-center d-flex justify-content-start">

			<button type="button" class="btn btn-warning mt-3"
				data-bs-toggle="modal" data-bs-target="#changePasswordModal">Change
				password</button>
		</div>
		<div class="col"></div>
	</div>

	<!-- Submit Block Modal -->
	<div class="modal fade" id="changePasswordModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content text-bg-dark">
				<div class="modal-body">
					<c:if
						test="${not empty requestScope.incorrectChangePasswordFormError}">
						<div class="alert alert-danger" role="alert">
							${requestScope.incorrectChangePasswordFormError}</div>
					</c:if>
					<p class="text-muted fs-5">Please provide your current password
						and a new one.</p>
					<hr class="style1">
					<form action="controller?action=changePassword" method="post">
					<div class="form-floating text-dark my-3"> 
					<input type="password" class="form-control" id="floatingPassword"
							placeholder="Password" name="currentPassword" required> <label for="floatingPassword">Current Password</label>
						</div>
						<div class="form-floating text-dark my-3"> 
					<input type="password" class="form-control" id="password"
							placeholder="Password" name="newPassword" required> <label for="floatingPassword">New Password</label>
						</div>
						<div class="form-floating text-dark my-3"> 
					<input type="password" class="form-control" id="confirm_password"
							placeholder="Password" required> <label for="floatingPassword">Confirm Password</label>
						</div>
						<hr class="style1 mt-2">
						<div class="d-flex justify-content-end">
					<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">Close</button>
					<div class="mx-2"></div>
					<button type="submit" class="btn btn-warning">Change
							password</button>
				</div>
						
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${not empty requestScope.incorrectChangePasswordFormError}">
	<script type="text/javascript">
		var myModal = new bootstrap.Modal(document
				.getElementById('changePasswordModal'), {})
		myModal.toggle()
	</script>
</c:if>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/validate_password_confirm.js"></script>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
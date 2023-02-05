<%@ include file="/WEB-INF/jspf/header.jspf"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>

<h3 class="text-center mb-4 pb-2 text-warning fw-bold"><fmt:message key = "nav.faq"/></h3>
  <p class="text-center mb-5">
    <fmt:message key="faq_header"/>
  </p>

  <div class="row text-white">
    <div class="col-md-6 col-lg-4 mb-4">
      <h6 class="mb-3 text-warning"><i class="far fa-paper-plane text-primary pe-2"></i> <fmt:message key="faq_what_services_q"/></h6>
      <p>
       <fmt:message key="faq_what_services_a"/>
      </p>
    </div>

    <div class="col-md-6 col-lg-4 mb-4">
      <h6 class="mb-3 text-warning"><i class="fas fa-pen-alt text-primary pe-2"></i> <fmt:message key="faq_registration_q"/></h6>
      <p>
         <fmt:message key="faq_registration_a"/>
      </p>
    </div>

    <div class="col-md-6 col-lg-4 mb-4">
      <h6 class="mb-3 text-warning"><i class="fas fa-user text-primary pe-2"></i> <fmt:message key="faq_view_tariff_q"/>
      </h6>
      <p>
       <fmt:message key="faq_view_tariff_a"/>
      </p>
    </div>

    <div class="col-md mb-4">
      <h6 class="mb-3 text-warning"><i class="fas fa-rocket text-primary pe-2"></i> <fmt:message key="faq_steps_sign_up_q"/>
      </h6>
      <p>
      <fmt:message key="faq_steps_sign_up_a"/>
      </p>
    </div>
  </div>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
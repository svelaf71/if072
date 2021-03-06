<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<%--
  Created by Nazar Vynnyk
 --%>

<div class="row">
    <div class="col-lg-6">
        <div class="form-group has-warning">
            <div class="panel-heading">
                <h1 class="page-header"><spring:message code="store.addNewStore"/></h1>
            </div>

            <div class="panel-body">

                <c:url var="addAction" value="/addStore/"/>

                <form:form role="form" action="${addAction}" method="POST" modelAttribute="store">
                    <fieldset>

                        <c:if test="${not empty validMessage}">
                            <div class="alert alert-danger">
                                <p><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                    <spring:message code="${validMessage}"/></p>
                            </div>
                        </c:if>

                        <div class="form-group">
                            <form:errors path="name" class="alert alert-danger" aria-hidden="true" element="div"/>
                            <label class="control-label" form="inputWarning"> <spring:message code="store.name"/>
                            </label>
                            <spring:message code='name' var="nameMessage"/>
                            <form:input path="name" class="form-control"
                                        placeholder="${nameMessage}" type="text"/>
                        </div>

                        <div class="form-group">
                            <form:errors path="address" class="alert alert-danger" aria-hidden="true" element="div"/>
                            <label class="control-label" form="inputWarning"><spring:message
                                    code="store.storeAddress"/></label>
                            <spring:message code='address' var="addressMessage"/>
                            <form:input path="address" class="form-control"
                                        placeholder="${addressMessage}" type="text"/>
                        </div>


                        <input type="submit" class="btn btn-lg btn-success btn-custom" value=<spring:message
                                code="store.addStore"/>/>
                        <input type="reset" class="btn btn-lg btn-reset-custom btn-reset-custom"
                               onclick="document.location.href='<c:url value="/stores/"/>'" value=<spring:message
                                code="cancel"/>>
                    </fieldset>
                </form:form>
            </div>
        </div>
    </div>
</div>


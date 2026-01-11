<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Register">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>

    <s:layout-component name="contents">
        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                            <c:if test="${!empty actionBean.context.validationErrors}">
                                   <s:errors/>
                            </c:if>
                    <s:form action="/web/register" name="createForm" id="createForm"
                            method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>

                        <table class="alternating">
                            <thead>
                            <tr>
                                <th colspan="2">
                                        <u><fmt:message key="registerheaderlabel"/></u>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <td>
                                    <fmt:message key="firstnamelabel"/>
                                    <input type = "text"  style="background-color:#F0E68C"
                                    name="firstName" value="${actionBean.firstName}"/>

                                </td>
                                <td>
                                    <fmt:message key="middlenamelabel"/>
                                    <input type = "text"  style="background-color:#F0E68C"
                                    name="middleName" value="${actionBean.middleName}"/>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <fmt:message key="lastnamelabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="lastName" value="${actionBean.lastName}"/>

                                </td>
                                <td>
                                    <fmt:message key="passwordlabel"/>
                                    <input type = "password"  style="background-color:#F0E68C"
                                    name="password" value=""/>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <fmt:message key="addresslabel"/>
                                    <input type = "text"  style="background-color:#F0E68C"
                                    name="address" value="${actionBean.address}"/>
                                </td>
                                <td>
                                    <fmt:message key="townlabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="town" value="${actionBean.town}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <fmt:message key="nationalidlabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="nationalId" value="${actionBean.nationalId}"/>
                                </td>
                               <td>
                                    <fmt:message key="mobilephonelabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="mobileNumber" value="${actionBean.mobileNumber}"/>
                                </td>
                            </tr>
                            <tr valign="top">
                                <td>
                                    <fmt:message key="doblabel"/> (DD-MM-YYYY)
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="dateOfBirth" id="dateOfBirth" class="dateOfBirth" value="${actionBean.dateOfBirth}"/>
                                </td>
                                <td>
                                    <fmt:message key="emaillabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="email" value="${actionBean.email}"/>
                                </td>
                            </tr>
                            <tr valign="top">
                               <td>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="country" id="country" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectcountrylabel"/></s:option>
                                                   <c:forEach items="${actionBean.countries}" var="country"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.country !=null &&
                                                             actionBean.country == country}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${country}" ${selectedValue}/>
                                                      ${country}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                </td>
                                <td>
                                    ${actionBean.securityQuestion1}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer1" value="${actionBean.answer1}"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ${actionBean.securityQuestion2}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer2" value="${actionBean.answer2}"/>
                                </td>
                                <td>
                                    ${actionBean.securityQuestion3}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer3" value="${actionBean.answer3}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="right" colspan="4">
                                    <fmt:message key="submitlabel" var="submitlabel"/>
                                    <d:submit class="small" name="save" value="${submitlabel}"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </s:form>
                </div>
            </div>
                    <script language ="javascript">
                            $(document).ready(function() {
                                  const dateOfBirth = $("#dateOfBirth");
                                  if (dateOfBirth) {
                                    dateOfBirth.datepicker({
                                      maxDate: 0,
                                      dateFormat: "dd-mm-yy",
                                      showAnim: "slideDown",
                                      duration: "fast",
                                      changeMonth: true,
                                      changeYear: true
                                    });
                                  }
                            });
                      </script>
        </div>
    </s:layout-component>
</s:layout-render>

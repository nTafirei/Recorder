<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Edit My Details">

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
                    <s:errors/>
                    <s:form action="/web/edit-my-details" name="createForm" id="createForm"
                            method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                        <table class="alternating">
                            <thead>
                            <tr>
                                <th colspan="2">
                                        <u><fmt:message key="edituserlabel"/> ${actionBean.currentUser.fullName}</u>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
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
                                        <fmt:message key="countrylabel"/>
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

                                <td colspan="2">
                                    <fmt:message key="mobilephonelabel"/>
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="mobileNumber" id="mobileNumber" class="dateOfBirth" value="${actionBean.mobileNumber}"/>
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

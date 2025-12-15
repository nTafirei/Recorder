<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Reset Password">

    <s:layout-component name="head">
     </s:layout-component>

    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                            <c:if test="${!empty actionBean.context.validationErrors}">
                                   <s:errors/>
                            </c:if>
                    <s:form action="/web/reset-password" name="createForm" id="createForm"
                            method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                         <input type="hidden" name="token" id="token" value="${actionBean.token}"/>

                        <table class="alternating">
                            <thead>
                            <tr>
                                <th colspan="2">
                                        <u><fmt:message key="resetpasswordheaderlabel"/></u>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                                <td>
                                    <fmt:message key="passwordlabel"/>
                                    <input type = "password"  style="background-color:#F0E68C"
                                    name="password" value=""/>
                                </td>
                                <td>
                                    ${actionBean.securityQuestion1}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer1" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ${actionBean.securityQuestion2}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer2" value=""/>
                                </td>
                                <td>
                                    ${actionBean.securityQuestion3}
                                    <input type = "text" style="background-color:#F0E68C"
                                    name="answer3" value=""/>
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
        </div>
    </s:layout-component>
</s:layout-render>

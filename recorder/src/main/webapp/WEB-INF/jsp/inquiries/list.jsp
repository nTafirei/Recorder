<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Messages">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <security:protected-element name="list-inquiries">
                        <c:if test="${empty actionBean.contactRequests}">
                            <fmt:message key="nomessagesfound"/>
                        </c:if>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.contactRequests}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="emaillabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="telephonelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="commentslabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.contactRequests}" var="inquiry"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                                ${inquiry.name}
                                        </td>
                                        <td>
                                                ${inquiry.email}
                                        </td>
                                        <td>
                                                ${inquiry.telephone}
                                        </td>
                                        <td>
                                                ${inquiry.comments}
                                        </td>
                                        <td>
                                            <d:link
                                                    href="/web/user/inquiries/delete/${inquiry.id}"><fmt:message
                                                    key="deletelabel"/></d:link>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/security/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>

                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

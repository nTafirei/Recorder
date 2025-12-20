<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="My Details">

    <s:layout-component name="head">
     </s:layout-component>

    <s:layout-component name="contents">
        <s:errors/>

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                        <table class="alternating">
                            <thead>
                                <tr>
                                    <th colspan="2">
                                        <u><fmt:message key="userdetailsheaderlabel"/></u>
                                    </th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="firstnamelabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.firstName}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="lastnamelabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.lastName}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="nationalidlabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.nationalId}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="addresslabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.address.address}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="citylabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.address.city}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="countrylabel"/>
                                </td>
                                <td>
                                        ${actionBean.user.address.country}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                   <fmt:message key="mobilephonelabel"/>
                                </td>
                                <td>
                                    ${actionBean.user.mobileNumber}
                                </td>
                            </tr>
                            <tr>
                                 <td class="strong">
                                    <fmt:message key="roleslabel"/>
                                 </td>
                                 <td>
                                     <c:forEach items="${actionBean.user.userRoles}" var="role"
                                                                                varStatus="loopStatus">
                                     ${role.roleName}
                                                <c:if test="${funcs:hasMore(loopStatus.index,actionBean.user.userRoles) == true}">
                                                 ,&nbsp;&nbsp;
                                                </c:if>
                                     </c:forEach>
                                 </td>
                             </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="emaillabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.user.email}
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="statuslabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.user.activeStatus.status}
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="actionlabel"/>
                                  </td>
                                <td>
                                    <d:link href="/web/edit-my-details">
                                                  <fmt:message key="editlabel"/>
                                    </d:link>
                                </td>
                              </tr>
                            </tbody>
                        </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

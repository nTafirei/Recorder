<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="User Details">

    <s:layout-component name="head">
     </s:layout-component>

    <s:layout-component name="contents">
        <s:errors/>

<security:protected-element name="show-user-list">
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
                                      <a href="mailto:${actionBean.user.email}">${actionBean.user.email}</a>
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
                                <td align="right" colspan="3">
                                                      <d:link
                                                          href="/web/user-recordings?user=${actionBean.user.id}">
                                                          <fmt:message key="userrecordingslabel"/>
                                                      </d:link> |
                                 <security:protected-element name="manage-roles">
                                                      <d:link
                                                          href="/web/assign-role/${actionBean.user.id}">
                                                          <fmt:message key="assignroleslabel"/>
                                                      </d:link>
                                                     | <d:link
                                                          href="/web/remove-role/${actionBean.user.id}">
                                                          <fmt:message key="removeroleslabel"/>
                                                      </d:link>
                                 </security:protected-element>
                                                              <security:protected-element name="edit-users">
                                                                  | <d:link
                                                                              href="/web/edit-user?user=${actionBean.user.id}">
                                                                              <fmt:message key="editlabel"/>
                                                                    </d:link>
                                                               </security:protected-element>
                                 <security:protected-element name="manage-users">
                                                 | <d:link
                                                             href="/web/user-transaction-history/${actionBean.user.id}">
                                                             <fmt:message key="accounthistorylabel"/>
                                                   </d:link>
                                </security:protected-element>
                                                <security:protected-element name="edit-users">
                                                   <c:if test="${actionBean.user.activeStatus == 'ACTIVE'}">
                                                        | <d:link
                                                                 href="/web/edit-user?user=${actionBean.user.id}&_eventName=disable">
                                                                 <fmt:message key="disablelabel"/>
                                                        </d:link>
                                                   </c:if>

                                                   <c:if test="${actionBean.user.activeStatus == 'NOT_ACTIVE'}">
                                                        | <d:link
                                                                 href="/web/edit-user?user=${actionBean.user.id}&_eventName=enable">
                                                                 <fmt:message key="enablelabel"/>
                                                        </d:link>
                                                     </c:if>
                                                      </security:protected-element>
                                          </td>
                                      </tr>
                            </tbody>
                        </table>
                </div>
            </div>
        </div>
        </security:protected-element>
    </s:layout-component>
</s:layout-render>

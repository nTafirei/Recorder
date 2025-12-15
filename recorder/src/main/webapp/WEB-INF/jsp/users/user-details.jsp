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
                             <c:if test="${actionBean.user.isAgent}">
                                <tr>
                                     <td class="strong">
                                        <fmt:message key="agentforlabel"/>
                                     </td>
                                     <td>
                                         ${actionBean.user.vendor.name}
                                     </td>
                                 </tr>
                             </c:if>
                             <c:if test="${actionBean.user.isVendorAdmin}">
                                <tr>
                                     <td class="strong">
                                        <fmt:message key="vendoradminforlabel"/>
                                     </td>
                                     <td>
                                         ${actionBean.user.vendor.name}
                                     </td>
                                 </tr>
                             </c:if>
                             <c:if test="${actionBean.user.isMerchantAdmin}">
                                <tr>
                                     <td class="strong">
                                        <fmt:message key="merchantadminforlabel"/>
                                     </td>
                                     <td>
                                         ${actionBean.user.merchant.name}
                                     </td>
                                 </tr>
                             </c:if>
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
                                     <fmt:message key="balance"/>
                                  </td>
                                  <td>
                                      ${actionBean.user.account.availableBalance.amount}
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
                                     <fmt:message key="loyaltypointslabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.rewardPoints.points}
                                      <security:protected-element name="redeem-user-points">
                                          <c:if test="${actionBean.rewardPoints.canRedeem}">
                                            | <d:link href="/web/redeem-user-points?user=${actionBean.user.id}">
                                                  <fmt:message key="redeemlabel"/> ${actionBean.redeemableAmount} ${actionBean.purchaseCurrency}
                                            </d:link>
                                          </c:if>
                                      </security:protected-element>
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="verified"/>?
                                  </td>
                                  <td>
                                      ${actionBean.user.verified.type}
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="iduploadedlabel"/>?
                                  </td>
                                  <td>

                                                       <c:if test="${!empty actionBean.user.photoId}">
                                                         Yes, <d:link
                                                                     href="/web/download-attachment?attachment=${actionBean.user.photoId.id}" target ="_blank">
                                                                     <fmt:message key="downloadlabel"/>
                                                         </d:link>
                                                        </c:if>
                                                       <c:if test="${empty actionBean.user.photoId}">
                                                       No, <d:link
                                                                 href="/web/upload?user=${actionBean.user.id}">
                                                                 <fmt:message key="uploadidlabel"/>
                                                        </d:link>
                                                        </c:if>
                                  </td>
                              </tr>
                            <tr>
    <td align="right" colspan="3">
                                                <c:if test="${actionBean.user.isAgent}">
                                                  <security:protected-element name="view-agent-float-history">
                                                      <d:link
                                                                 href="/web/agent-float-history?agent=${actionBean.user.id}">
                                                                 <fmt:message key="floathistorylabel"/>
                                                       </d:link>
                                                   </security:protected-element>
                                                </c:if>
                                                 <security:protected-element name="top-up-users">
                                                     | <d:link
                                                                 href="/web/top-up?user=${actionBean.user.id}">
                                                                 <fmt:message key="topuplabel"/>
                                                       </d:link>
                                                 </security:protected-element>
                                 <security:protected-element name="disable-users">
                                                      |<d:link
                                                          href="/web/disable/${actionBean.user.id}">
                                                          <fmt:message key="disablelabel"/>
                                                      </d:link>
                                 </security:protected-element>
                                 <security:protected-element name="manage-roles">
                                                      | <d:link
                                                          href="/web/assign-role/${actionBean.user.id}">
                                                          <fmt:message key="assignroleslabel"/>
                                                      </d:link>
                                                     | <d:link
                                                          href="/web/remove-role/${actionBean.user.id}">
                                                          <fmt:message key="removeroleslabel"/>
                                                      </d:link>
                                                    <c:if test="${actionBean.user.isAgent}">
                                                         | <d:link
                                                                     href="/web/manage-agent-role?user=${actionBean.user.id}&_eventName=demote">
                                                                     <fmt:message key="demotefromgentlabel"/>
                                                           </d:link>
                                                    </c:if>
                                                    <c:if test="${actionBean.user.isAgent == false}">
                                                         | <d:link
                                                                     href="/web/manage-agent-role?user=${actionBean.user.id}&_eventName=choose">
                                                                     <fmt:message key="makeagentlabel"/>
                                                           </d:link>
                                                    </c:if>
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

                                                <c:if test="${actionBean.user.verified == 'NO'}">
                                                     <security:protected-element name="verify-users">
                                                           |
                                                           <d:link
                                                           href="/web/verify-user?user=${actionBean.user.id}&_eventName=verify">
                                                           <fmt:message key="verifyuserlabel"/>
                                                           </d:link>
                                                     </security:protected-element>
                                                </c:if>
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
                                                        | <d:link
                                                                 href="/web/upload?user=${actionBean.user.id}">
                                                                 <fmt:message key="uploadidlabel"/>
                                                        </d:link>
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

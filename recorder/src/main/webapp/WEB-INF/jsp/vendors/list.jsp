<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Vendors">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                    <security:protected-element name="view-vendors">
                        <c:if test="${empty actionBean.vendors}">
                            <strong><fmt:message key="novendorsfound">
                                <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                            </fmt:message>
                            </strong>
                        </c:if>
                        <table width="100%">
                            <tr>
                                <td align="center" colspan = "3">
                                    <c:if test="${!empty actionBean.vendors}">
                                        <strong>${actionBean.vendorsSize}
                                        <fmt:message key="vendorsfound">
                                         <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                                        </strong>
                                    </c:if>
                                </td>
                                <c:if test="${actionBean.isLoggedIn == true}">
                                    <security:protected-element name="manage-vendors">
                                        <td align="center" colspan="3">
                                            <d:link href="/web/new-vendor">
                                                <fmt:message key="newvendorlabel"/>
                                            </d:link>
                                        | <d:link
                                              href="/web/vendors?activeStatus=ACTIVE">
                                                <fmt:message key="activelabel"/>
                                              </d:link>
                                          |
                                            <d:link
                                              href="/web/vendors?activeStatus=NOT_ACTIVE">
                                                <fmt:message key="notactivelabel"/>
                                            </d:link>
                                        </td>
                                    </security:protected-element>
                                </c:if>
                            </tr>
                        </table>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.vendors}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="balance"/>
                                    </td>
                                    <td>
                                        <fmt:message key="emaillabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="contactpersonlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="phonenumberslabel"/>
                                    </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.vendors}" var="vendor"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${vendor.name}
                                        </td>
                                        <td>
                                            ${vendor.activeStatus.status}
                                        </td>
                                        <td>
                                           ${vendor.account.availableBalance.amount} ${vendor.account.availableBalance.currency}
                                        </td>
                                        <td>
                                            ${vendor.email}
                                        </td>
                                        <td>
                                            ${vendor.contactPerson}
                                        </td>
                                        <td>
                                            ${vendor.phoneNumbers}
                                        </td>
                                        <td align="right">
                                           <security:protected-element name="manage-vendors">
                                                      <d:link
                                                                 href="/web/vendor-agents?vendor=${vendor.id}&agentStatus=ACTIVE">
                                                                 <fmt:message key="agentslabel"/> (${vendor.numAgents})
                                                       </d:link>
                                                      | <d:link
                                                                 href="/web/edit-vendor?vendor=${vendor.id}">
                                                                 <fmt:message key="editlabel"/>
                                                       </d:link>
                                                 <c:if test="${vendor.activeStatus == 'ACTIVE'}">
                                                     | <d:link
                                                                 href="/web/vendor/${vendor.id}/disable?vendor=${vendor.id}">
                                                                 <fmt:message key="disablelabel"/>
                                                       </d:link>
                                                 </c:if>
                                                 <c:if test="${vendor.activeStatus == 'NOT_ACTIVE'}">
                                                     | <d:link
                                                                 href="/web/vendor/${vendor.id}/enable?vendor=${vendor.id}">
                                                                 <fmt:message key="enablelabel"/>
                                                       </d:link>
                                                 </c:if>

                                                     | <d:link
                                                                 href="/web/top-up-vendor?vendor=${vendor.id}">
                                                                 <fmt:message key="topuplabel"/>
                                                       </d:link>
                                                 </security:protected-element>
                                                 <security:protected-element name="view-vendor-transactions">
                                                  | <d:link
                                                              href="/web/vendor-transaction-history/${vendor.id}">
                                                              <fmt:message key="transactionhistorylabel"/>
                                                    </d:link>
                                                 </security:protected-element>
                                                 <security:protected-element name="view-vendor-details">
                                                  | <d:link
                                                              href="/web/org-details?org=${vendor.id}&_eventName=details">
                                                              <fmt:message key="detailslabel"/>
                                                    </d:link>
                                                 </security:protected-element>
                                      </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
                    <security:when-no-content-displayed>
                        <d:link href="/web/user/site/help"><fmt:message
                                key="securityexceptionlink"/></d:link>
                    </security:when-no-content-displayed>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

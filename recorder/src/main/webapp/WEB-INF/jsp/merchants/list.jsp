<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Merchants">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                        <c:if test="${empty actionBean.merchants}">
                            <strong><fmt:message key="nomerchantsfound">
                                         <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                            </strong>
                        </c:if>
                        <table width="100%">
                            <tr>
                                <td align="center" colspan = "3">
                                    <c:if test="${!empty actionBean.merchants}">
                                        <strong>${actionBean.merchantsSize}
                                        <fmt:message key="merchantsfound">
                                         <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                                        </strong>
                                    </c:if>
                                </td>
                                <c:if test="${actionBean.isLoggedIn == true}">
                                    <security:protected-element name="manage-merchants">
                                        <td align="center" colspan="3">
                                        <d:link href="/web/new-merchant">
                                            <fmt:message key="newmerchantlabel"/>
                                        </d:link>
                                        | <d:link
                                              href="/web/merchants?activeStatus=ACTIVE">
                                                <fmt:message key="activelabel"/>
                                              </d:link>
                                          |
                                            <d:link
                                              href="/web/merchants?activeStatus=NOT_ACTIVE">
                                                <fmt:message key="notactivelabel"/>
                                            </d:link>
                                        </td>
                                    </security:protected-element>
                                </c:if>
                            </tr>
                        </table>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.merchants}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
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
                                <c:forEach items="${actionBean.merchants}" var="merchant"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${merchant.name}
                                        </td>
                                        <td>
                                            ${merchant.activeStatus.status}
                                        </td>
                                        <td>
                                            ${merchant.email}
                                        </td>
                                        <td>
                                            ${merchant.contactPerson}
                                        </td>
                                        <td>
                                            ${merchant.phoneNumbers}
                                        </td>
                                        <td align="right">
                                             <d:link
                                                 href="/web/merchant-products?merchant=${merchant.id}&activeStatus=${merchant.activeStatus}">
                                                 <fmt:message key="merchantproductslabel"/>
                                            </d:link>
                                        <security:protected-element name="manage-merchants">
                                                    | <d:link href="/web/new-product?merchant=${merchant.id}">
                                                        <fmt:message key="newproductlabel"/>
                                                    </d:link>
                                                     | <d:link
                                                                 href="/web/edit-merchant?merchant=${merchant.id}">
                                                                 <fmt:message key="editlabel"/>
                                                       </d:link>
                                                 <c:if test="${merchant.activeStatus == 'ACTIVE'}">
                                                     | <d:link
                                                                 href="/web/merchant/${merchant.id}/disable?merchant=${merchant.id}">
                                                                 <fmt:message key="disablelabel"/>
                                                       </d:link>
                                                 </c:if>
                                                 <c:if test="${merchant.activeStatus == 'NOT_ACTIVE'}">
                                                     | <d:link
                                                                 href="/web/merchant/${merchant.id}/enable?merchant=${merchant.id}">
                                                                 <fmt:message key="enablelabel"/>
                                                       </d:link>
                                                 </c:if>
                                                 <security:protected-element name="view-merchant-transactions">
                                                  | <d:link
                                                              href="/web/merchant-transaction-history/${merchant.id}">
                                                              <fmt:message key="transactionhistorylabel"/>
                                                    </d:link>
                                                 </security:protected-element>
                                                 <security:protected-element name="view-vendor-details">
                                                  | <d:link
                                                              href="/web/org-details?org=${merchant.id}&_eventName=details">
                                                              <fmt:message key="detailslabel"/>
                                                    </d:link>
                                                 </security:protected-element>
                                        </security:protected-element>
                                      </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

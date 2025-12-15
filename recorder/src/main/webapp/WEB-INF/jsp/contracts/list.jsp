<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Contracts">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <s:errors/>
                     <security:protected-element name="manage-contracts">
                                                    <d:link
                                                        href="/web/new-contract"><fmt:message
                                                        key="addcontractlabel"/></d:link>
                      </security:protected-element>

                    <security:protected-element name="view-contracts">
                         <table class="alternating">
                            <tr>
                                <td>
                                    <c:if test="${empty actionBean.contracts}">
                                        <fmt:message key="nocontractsfound">
                                         <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                                    </c:if>
                                    <c:if test="${!empty actionBean.contracts}">
                                        ${actionBean.contractsSize}
                                        <fmt:message key="contractsfound">
                                         <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                                    </c:if>
                                </td>
                                <td>
                                         <d:link
                                              href="/web/contracts?activeStatus=ACTIVE">
                                                <fmt:message key="activelabel"/>
                                              </d:link>
                                          |
                                            <d:link
                                              href="/web/contracts?activeStatus=NOT_ACTIVE">
                                                <fmt:message key="notactivelabel"/>
                                            </d:link>
                                        </td>
                            </tr>
                         </table>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.contracts}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="startdatelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="enddatelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="renewaldatelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="minpurchaselabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="maxpurchaselabel"/>
                                    </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.contracts}" var="contract"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                                ${contract.contractor}
                                        </td>
                                        <td>
                                                ${contract.activeStatus.status}
                                        </td>
                                        <td>
                                                ${contract.startDate}
                                        </td>
                                        <td>
                                                ${contract.endDate}
                                        </td>
                                        <td>
                                                ${contract.renewalDate}
                                        </td>
                                        <td>
                                            <c:if test="${!empty contract.minPurchase}">
                                                ${actionBean.purchaseCurrency}
                                                ${contract.minPurchase}
                                                per ${contract.contractTerm.term}
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${!empty contract.maxPurchase}">
                                                ${actionBean.purchaseCurrency}
                                                ${contract.maxPurchase}
                                                per ${contract.contractTerm.term}
                                            </c:if>
                                        </td>
                                        <td align="right">
                                            <security:protected-element name="manage-contracts">
                                                <d:link
                                                    href="/web/edit-contract?contract=${contract.id}&_eventName=edit"><fmt:message
                                                    key="editlabel"/></d:link>
                                                <c:if test="${contract.activeStatus == 'ACTIVE'}">
                                                    | <d:link
                                                        href="/web/edit-contract?contract=${contract.id}&_eventName=disable"><fmt:message
                                                        key="disablelabel"/></d:link>
                                                </c:if>
                                                <c:if test="${contract.activeStatus == 'NOT_ACTIVE'}">
                                                    | <d:link
                                                        href="/web/edit-contract?contract=${contract.id}&_eventName=enable"><fmt:message
                                                        key="enablelabel"/></d:link>
                                                </c:if>
                                            </security:protected-element>
                                                | <d:link
                                                    href="/web/contract-details?contract=${contract.id}"><fmt:message
                                                    key="detailslabel"/></d:link>

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

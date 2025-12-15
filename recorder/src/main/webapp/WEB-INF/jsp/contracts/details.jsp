<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Contract Details">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <s:errors/>
                    <security:protected-element name="view-contracts">

                        <table class="alternating">
                            <c:if test="${!empty actionBean.contract}">
                               <tbody>
                                  <tr>
                                    <td colspan="3" align="center">
                                        <fmt:message key="contractdetailslabel"/>
                                    </td>
                                  </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                        <td>
                                                ${actionBean.contract.contractor}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="statuslabel"/>
                                    </td>
                                        <td>
                                                ${actionBean.contract.activeStatus.status}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="startdatelabel"/>
                                    </td>
                                        <td>
                                                ${actionBean.contract.startDate}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="enddatelabel"/>
                                    </td>
                                        <td>
                                                ${actionBean.contract.endDate}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="renewaldatelabel"/>
                                    </td>
                                        <td>
                                                ${actionBean.contract.renewalDate}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="minpurchaselabel"/>
                                    </td>
                                        <td>
                                            <c:if test="${!empty actionBean.contract.minPurchase}">
                                                ${actionBean.purchaseCurrency}
                                                ${actionBean.contract.minPurchase}
                                                per ${actionBean.contract.contractTerm.term}
                                            </c:if>
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="maxpurchaselabel"/>
                                    </td>
                                        <td>
                                            <c:if test="${!empty actionBean.contract.maxPurchase}">
                                                ${actionBean.purchaseCurrency}
                                                ${actionBean.contract.maxPurchase}
                                                per ${actionBean.contract.contractTerm.term}
                                            </c:if>
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="productsabel"/>
                                    </td>
                                    <td>
                                        <c:forEach items="${actionBean.contract.products}" var="product"
                                                   varStatus="loopStatus">
                                           ${product.name}
                                           <security:protected-element name="manage-contracts">
                                                | <d:link
                                                    href="/web/edit-contract?contract=${actionBean.contract.id}&product=${product.id}&_eventName=delete-product">
                                                    <fmt:message key="deletelabel"/></d:link><br/>
                                            </security:protected-element>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="attachmenteslabel"/>
                                    </td>
                                    <td>
                                        <c:if test="${empty actionBean.contract.attachments}">
                                                    <d:link
                                                            href="/web/edit-contract?contract=${actionBean.contract.id}&_eventName=edit"><fmt:message
                                                            key="uploadnowlabel"/></d:link>
                                        </c:if>
                                        <c:forEach items="${actionBean.contract.attachments}" var="attachment"
                                                   varStatus="loopStatus">

                                                <d:link
                                                    href="/web/download-attachment?attachment=${attachment.id}" target="_blank">
                                                    ${attachment.name}</d:link>
                                                <security:protected-element name="manage-contracts">
                                                | <d:link
                                                    href="/web/edit-contract?contract=${actionBean.contract.id}&toDelete=${attachment.id}&_eventName=delete-attachment">
                                                    <fmt:message key="deletelabel"/></d:link><br/>
                                                </security:protected-element>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <security:protected-element name="manage-contracts">
                                    <tr>
                                        <td>
                                            <fmt:message key="actionlabel"/>
                                        </td>
                                        <td>
                                                <d:link
                                                    href="/web/edit-contract?contract=${actionBean.contract.id}&_eventName=edit"><fmt:message
                                                    key="editlabel"/></d:link>
                                        </td>
                                    </tr>
                                </security:protected-element>
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

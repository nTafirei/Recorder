<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="${actionBean.merchant.name} Products">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                        <c:if test="${empty actionBean.products}">
                            <strong>
                            <fmt:message key="noproductsfound">
                                 <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                            </fmt:message>
                            for ${actionBean.merchant.name}</strong>
                        </c:if>
                        <table width="100%">
                            <tr>
                                <td align="center" colspan = "3">
                                    <c:if test="${!empty actionBean.products}">
                                        <strong>${actionBean.productsSize}
                                        <fmt:message key="productsfound">
                                          <fmt:param>${actionBean.activeStatus.status}</fmt:param>
                                        </fmt:message>
                                        for ${actionBean.merchant.name} (status:${actionBean.merchant.activeStatus.status})</strong>
                                    </c:if>
                                </td>
                                <c:if test="${actionBean.isLoggedIn == true}">
                                    <security:protected-element name="manage-products">
                                        <td align="center" colspan="3">
                                        <d:link href="/web/new-product">
                                            <fmt:message key="newproductlabel"/>
                                        </d:link>
                                        | <d:link
                                              href="/web/merchant-products?activeStatus=ACTIVE&merchant=${actionBean.merchant.id}">
                                                <fmt:message key="activelabel"/>
                                              </d:link>
                                          |
                                            <d:link
                                              href="/web/merchant-products?activeStatus=NOT_ACTIVE&merchant=${actionBean.merchant.id}">
                                                <fmt:message key="notactivelabel"/>
                                            </d:link>
                                        </td>
                                    </security:protected-element>
                                </c:if>
                            </tr>
                        </table>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.products}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="namelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="typelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="statuslabel"/>
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
                                <c:forEach items="${actionBean.products}" var="product"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${product.name}
                                        </td>
                                        <td>
                                            ${product.productType.type}
                                        </td>
                                        <td>
                                            ${product.activeStatus.status}
                                        </td>
                                       <td>
                                            ${product.minPurchase}
                                        </td>
                                       <td>
                                            ${product.maxPurchase}
                                        </td>
                                        <td align="right">
                                            <c:if test="${product.activeStatus == 'ACTIVE'}">
                                                 <d:link
                                                     href="/web/purchase?product=${product.id}">
                                                     <fmt:message key="purchaselabel"/> |
                                                </d:link>
                                            </c:if>
                                                 <security:protected-element name="view-merchant-transactions">
                                                   <d:link
                                                              href="/web/product-transaction-history/${product.id}">
                                                              <fmt:message key="transactionhistorylabel"/> |
                                                    </d:link>
                                                 </security:protected-element>
                                        <security:protected-element name="manage-products">
                                                      <d:link
                                                                 href="/web/edit-product?product=${product.id}">
                                                                 <fmt:message key="editlabel"/> | 
                                                       </d:link>
                                                 <c:if test="${product.activeStatus == 'ACTIVE'}">
                                                     <d:link
                                                                 href="/web/product/${product.id}/disable?product=${product.id}">
                                                                 <fmt:message key="disablelabel"/> |
                                                       </d:link>
                                                 </c:if>
                                                 <c:if test="${product.merchant.activeStatus == 'ACTIVE' and product.activeStatus == 'NOT_ACTIVE'}">
                                                     <d:link
                                                                 href="/web/product/${product.id}/enable?product=${product.id}">
                                                                 <fmt:message key="enablelabel"/>
                                                       </d:link>
                                                 </c:if>
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

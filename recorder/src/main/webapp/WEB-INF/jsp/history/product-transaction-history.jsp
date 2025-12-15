<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Product Transaction History">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">

    <security:protected-element name="view-merchant-transactions">
        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                        <c:if test="${empty actionBean.transactions}">
                            <strong><fmt:message key="nproductttransactionsfound"/>
                            for ${actionBean.product.name}.
                             </strong>
                        </c:if>
                        <s:form action="/web/product-transaction-history/${actionBean.product.id}" name="createForm" id="createForm" method="post">
                            <table width="100%">
                                <tr>
                                    <td align="center" colspan = "3">
                                        <c:if test="${!empty actionBean.transactions}">
                                            <strong>
                                            <fmt:message key="showinglabel"/>
                                            ${actionBean.page.numItemsShowing} of
                                            ${actionBean.page.totalItemsFound}.
                                            ${actionBean.page.totalItemsFound}<fmt:message key="producttransactions"/> for ${actionBean.product.name},
                                            page is ${actionBean.page.currPage}.
                                                    <br/>
                                                    <c:forEach items="${actionBean.page.pageNumbers}" var="pageItem"
                                                                                              varStatus="loopStatus">
                                                      <d:link href="/web/product-transaction-history/${actionBean.product.id}?currPage=${pageItem}">${pageItem}</d:link> |
                                                    </c:forEach>
                                        </c:if>
                                    </td>
                                        <td>
                                            <fmt:message key="startdatelabel"/> (dd-mm-yyyy)
                                            <d:text style="background-color:#F0E68C" name="startDate" id="startDate" class="startDate"/>
                                        </td>
                                        <td>
                                            <fmt:message key="enddatelabel"/> (dd-mm-yyyy)
                                            <d:text style="background-color:#F0E68C" name="endDate" id="endDate" class="endDate"/>
                                        </td>
                                        <td align="right" valign="bottom">
                                            <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                            <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                        </td>
                                </tr>
                            </table>
                        </s:form>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.transactions}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="buyermobilephonelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="beneficiarymobilelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="metrenumberlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="productlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="paymenttypelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="accountorcardnumberlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="accountnamelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="vouchernumberlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="detailslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.transactions}" var="transaction"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${transaction.formattedDateCreated}
                                        </td>
                                        <td>
                                            ${transaction.buyerMobileNumber}
                                        </td>
                                        <td>
                                            ${transaction.beneficiaryMobileNumber}
                                        </td>
                                        <td>
                                            ${transaction.meterNumber}
                                        </td>
                                        <td>
                                             ${transaction.product.name}
                                        </td>
                                        <td>
                                            ${transaction.paymentMethod.paymentType.type}
                                        </td>
                                        <td>
                                            ${transaction.maskedAccountNumber}
                                        </td>
                                        <td>
                                             ${transaction.accountName}
                                        </td>
                                        <td>
                                           ${transaction.currency} ${transaction.originalAmount}
                                        </td>
                                        <td>
                                            ${transaction.voucherNumber}
                                        </td>
                                        <td>
                                          <d:link
                                              href="/web/transaction-details?voucher=${transaction.id}">
                                              <fmt:message key="detailslabel"/>
                                           </d:link>
                                        </td>
                                    </tr>
                                </c:forEach>
                                    <tr>
                                        <td colspan="7" align="right">
                                                    <d:link target="_blank" href="/web/product-transaction-history?exportType=PDF&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&product=${actionBean.product.id}">
                                                        <fmt:message key="exportropdflabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/product-transaction-history?exportType=EXCEL&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&product=${actionBean.product.id}">
                                                        <fmt:message key="exporttoexcellabel"/>
                                                    </d:link>
                                                    | <d:link target="_blank" href="/web/product-transaction-history?exportType=CSV&_eventName=export&currPage=${actionBean.page.currPage}&startDate=${actionBean.startDate}&endDate=${actionBean.endDate}&product=${actionBean.product.id}">
                                                        <fmt:message key="exporttotextlabel"/>
                                                    </d:link>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:if>
                        </table>
                </div>
            </div>
                            <script language="javascript">
                                $(document).ready(function() {
                                      const startDate = $("#startDate");
                                      if (startDate) {
                                        startDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }
                                     const endDate = $("#endDate");
                                      if (endDate) {
                                        endDate.datepicker({
                                          maxDate: 0,
                                          dateFormat: "dd-mm-yy",
                                          showAnim: "slideDown",
                                          duration: "fast",
                                          changeMonth: true,
                                          changeYear: true
                                        });
                                      }
                                });
                            </script>
        </div>
     </security:protected-element>
    </s:layout-component>
</s:layout-render>

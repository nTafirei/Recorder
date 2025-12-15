<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Voucher ${actionBean.voucher.voucherNumber} Details">

    <s:layout-component name="head">
        <link rel="stylesheet" href="${actionBean.cssPath}/jquery-ui.css"/>
        <script src="${actionBean.scriptPath}/jquery-3.6.0.min.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui.js"></script>
        <script src="${actionBean.scriptPath}/jquery-ui-timepicker-addon.min.js"></script>
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                        <c:if test="${empty actionBean.voucher}">
                            <strong><fmt:message key="accounteventnotfound"/></strong>
                        </c:if>
                        <c:if test="${!empty actionBean.voucher}">
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        <fmt:message key="vouchernumberlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.voucherNumber}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.formattedDateCreated}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="activitytypelabel"/>
                                    </td>
                                    <td>
                                       ${actionBean.voucher.activityType.label}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="productlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.product.name}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="buyerlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.user.fullName}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="buyermobilephonelabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.buyerMobileNumber}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="beneficiarymobilelabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.beneficiaryMobileNumber}
                                    </td>
                                </tr>
                                <c:if test="${actionBean.voucher.product.requiresMetre}">
                                    <tr>
                                        <td>
                                            <fmt:message key="metrenumberlabel"/>
                                        </td>
                                        <td>
                                             ${actionBean.voucher.meterNumber}
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>
                                        <fmt:message key="paymenttypelabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.paymentMethod.paymentType.type}
                                    </td>
                                </tr>

                                <c:if test="${!empty actionBean.voucher.accountName}">
                                    <tr>
                                        <td>
                                            <fmt:message key="accountorcardnumberlabel"/>
                                        </td>
                                        <td>
                                             ${actionBean.voucher.maskedAccountNumber}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <fmt:message key="accountnamelabel"/>
                                        </td>
                                        <td>
                                             ${actionBean.voucher.accountName}
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>
                                        <fmt:message key="originalamountlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.currency}
                                          ${actionBean.voucher.originalAmount}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="effectiveamountlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.currency}
                                          ${actionBean.voucher.currencyAmount}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="basecurrencyamountlabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.baseCurrency}
                                          ${actionBean.voucher.baseCurrencyAmount}
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="exchangeratelabel"/>
                                    </td>
                                    <td>
                                        ${actionBean.voucher.exchangeRate.rate}
                                         ${actionBean.voucher.baseCurrency}
  
                                         for 1
                                         ${actionBean.voucher.exchangeRate.currency}
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <fmt:message key="aggregatorcommissionlabel"/>
                                    </td>
                                    <td>
                                        ${actionBean.voucher.currency} ${actionBean.voucher.aggCommission}
                                        at ${actionBean.voucher.aggCommissionRate.amount}
                                        ${actionBean.voucher.aggCommissionRate.chargeType.type}
                                    </td>
                                </tr>
                                <c:if test="${!empty actionBean.voucher.vendorCommission}">
                                    <tr>
                                        <td>
                                            <fmt:message key="vendorcommissionlabel"/>
                                        </td>
                                        <td>
                                                ${actionBean.voucher.currency} ${actionBean.voucher.vendorCommission}
                                                at ${actionBean.voucher.vendorCommissionRate.amount}
                                                ${actionBean.voucher.vendorCommissionRate.chargeType.type}
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.voucher.agentCommission}">
                                    <tr>
                                        <td>
                                            <fmt:message key="agentcommissionlabel"/>
                                        </td>
                                        <td>
                                                ${actionBean.voucher.currency} ${actionBean.voucher.agentCommission}
                                                at ${actionBean.voucher.agentCommissionRate.amount}
                                                ${actionBean.voucher.agentCommissionRate.chargeType.type}
                                        </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>
                                        <fmt:message key="taxlabel"/>
                                    </td>
                                    <td>
                                       ${actionBean.voucher.currency}  ${actionBean.voucher.tax}
                                       at ${actionBean.voucher.taxRate.amount}
                                       ${actionBean.voucher.taxRate.chargeType.type}
                                    </td>
                                </tr>
                                <tr>
                                    <td>

                                        <fmt:message key="totalchargeslabel"/>
                                    </td>
                                    <td>
                                         ${actionBean.voucher.currency}
                                         ${actionBean.voucher.totalCharges}
                                    </td>
                                </tr>

                                <security:protected-element name="view-user-account-balance">
                                    <tr>
                                        <td>
                                            <fmt:message key="balanceafterlabel"/>
                                        </td>
                                        <td>
                                           ${actionBean.voucher.currency}
                                           ${actionBean.voucher.balance}
                                        </td>
                                    </tr>
                                </security:protected-element>
                                </tbody>
                          </table>
                        </c:if>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

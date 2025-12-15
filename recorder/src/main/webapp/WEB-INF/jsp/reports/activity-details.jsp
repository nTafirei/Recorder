<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Activity Details">

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
                        <table class="alternating">
                            <c:if test="${!empty actionBean.activity}">
                                <thead>
                                    <th colspan="2">
                                        <fmt:message key="activitydetailslabel"/>
                                    </th>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                        <td>
                                            ${actionBean.activity.formattedDateCreated}
                                        </td>
                                </tr>
                                <c:if test="${!empty actionBean.activity.actor}">
                                    <tr>
                                        <td>
                                            <fmt:message key="actorlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.actor.fullName}
                                            </td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                        <td>
                                            ${actionBean.activity.title}
                                        </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="typelabel"/>
                                    </td>
                                        <td>
                                            ${actionBean.activity.activityType.label}
                                        </td>
                                </tr>
                                <c:if test="${!empty actionBean.activity.originalAmount}">
                                    <tr>
                                        <td>
                                            <fmt:message key="originalamountlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.currency}
                                                ${actionBean.activity.originalAmount}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.amount}">
                                    <tr>
                                        <td>
                                            <fmt:message key="amountlabel"/>
                                        </td>
                                            <td>
                                                 ${actionBean.activity.currency}
                                                ${actionBean.activity.amount}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.user}">
                                    <tr>
                                        <td>
                                            <fmt:message key="userlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.user.fullName}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.merchant}">
                                    <tr>
                                        <td>
                                            <fmt:message key="merchantlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.merchant.name}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.vendor}">
                                    <tr>
                                        <td>
                                            <fmt:message key="vendorlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.vendor.name}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.account}">
                                    <tr>
                                        <td>
                                            <fmt:message key="accountlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.account.accountName}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.product}">
                                    <tr>
                                        <td>
                                            <fmt:message key="productlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.product.name}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.voucher}">
                                    <tr>
                                        <td>
                                            <fmt:message key="voucherlabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.voucher.voucherNumber}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.chargeRate}">
                                    <tr>
                                        <td>
                                            <fmt:message key="chargeratelabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.chargeRate.currency}
                                                ${actionBean.activity.chargeRate.amount}
                                                ${actionBean.activity.chargeRate.chargeType.type}
                                                <br/>
                                                <fmt:message key="basecurrencylabel"/>:
                                                ${actionBean.activity.chargeRate.baseCurrency}
                                            </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!empty actionBean.activity.exchangeRate}">
                                    <tr>
                                        <td>
                                            <fmt:message key="exchangeratelabel"/>
                                        </td>
                                            <td>
                                                ${actionBean.activity.exchangeRate.rate}
                                                ${actionBean.activity.exchangeRate.baseCurrency}
                                                to
                                                1 ${actionBean.activity.exchangeRate.currency}
                                            </td>
                                    </tr>
                                </c:if>
                                </tbody>
                           </c:if>
                        </table>
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
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

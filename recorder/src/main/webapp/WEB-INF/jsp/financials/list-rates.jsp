<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="View Exchange Rates">

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

                    <security:protected-element name="view-exchange-rates">
                    <s:errors/>
                        <c:if test="${empty actionBean.exchangeRates}">
                            <strong><fmt:message key="noexchangeratesfound"/> for ${actionBean.searchDates}</strong>
                        </c:if>

                        <table width="100%">
                            <tr>
                                <td align="center" colspan = "3">
                                    <c:if test="${!empty actionBean.exchangeRates}">
                                        <strong>${actionBean.exchangeRatesSize}
                                        <fmt:message key="exchangeratesfound"/> for ${actionBean.searchDates}</strong>
                                    </c:if>
                                </td>
                             </tr>
                                 <tr>
                                    <td>
                                        <security:protected-element name="view-system-wallet-account">
                                          <d:link
                                              href="/web/account-report?account=${actionBean.systemWalletAccount.id}">
                                              <fmt:message key="systemaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                        <security:protected-element name="view-system-commissions-account">
                                          | <d:link
                                              href="/web/account-report?account=${actionBean.commissionsAccount.id}">
                                              <fmt:message key="systemcommisionsaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-tax-authority-account">
                                          | <d:link
                                              href="/web/account-report?account=${actionBean.taxAccount.id}">
                                              <fmt:message key="taxauthaccountlabel"/>
                                           </d:link>
                                      </security:protected-element>
                                      <security:protected-element name="view-exchange-rates">
                                           | <d:link
                                              href="/web/rates">
                                              <fmt:message key="rateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-charge-rates">
                                           | <d:link
                                              href="/web/charge-rates">
                                              <fmt:message key="chargerateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-loyalty-rates">
                                           | <d:link
                                              href="/web/loyalty-rates">
                                              <fmt:message key="loyaltyrateslabel"/>
                                           </d:link>
                                       </security:protected-element>
                                    </td>
                                </tr>
                       </table>
                       <s:form action="/web/rates" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                            <table width="100%">
                                <tr>
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
                            <c:if test="${!empty actionBean.exchangeRates}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="currencylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="basecurrencylabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="ratelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="dateslabel"/>
                                    </td>
                                    <td align="right">
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.exchangeRates}" var="exchangeRate"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${exchangeRate.currency} (${exchangeRate.fullName})
                                        </td>
                                        <td>
                                            ${exchangeRate.baseCurrency}
                                        </td>
                                        <td>
                                            ${exchangeRate.rate}
                                        </td>
                                        <td>
                                            ${actionBean.searchDates}
                                        </td>
                                        <td align="right">
                                        <c:if test="${exchangeRate.canBeEdited}">
                                            <security:protected-element name="manage-exchange-rates">
                                                         <d:link
                                                                     href="/web/edit-rate?exchangeRate=${exchangeRate.id}">
                                                                     <fmt:message key="editlabel"/>
                                                         </d:link>
                                            </security:protected-element>
                                        </c:if >
                                      </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </c:if>
                        </table>
                    </security:protected-element>
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
    </s:layout-component>
</s:layout-render>

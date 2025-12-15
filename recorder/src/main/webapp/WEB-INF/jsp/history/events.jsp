<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Account Events">

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
                        <c:if test="${empty actionBean.accountEvents}">
                            <strong><fmt:message key="noaccounteventsfound"/> for ${actionBean.account.accountName}.
                             <fmt:message key="balance"/>: ${actionBean.account.availableBalance.amount}
                             </strong>
                             <br/>
                        </c:if>
                                <security:protected-element name="view-system-wallet-account">
                                          <d:link
                                              href="/web/account-transaction-history?account=${actionBean.systemWalletAccount.id}">
                                              <fmt:message key="systemaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                        <security:protected-element name="view-system-commissions-account">
                                          | <d:link
                                              href="/web/account-transaction-history?account=${actionBean.commissionsAccount.id}">
                                              <fmt:message key="systemcommisionsaccountlabel"/>
                                           </d:link>
                                       </security:protected-element>
                                      <security:protected-element name="view-tax-authority-account">
                                          | <d:link
                                              href="/web/account-transaction-history?account=${actionBean.taxAccount.id}">
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
                                </td>
                            </tr>
                        </table>
                       <s:form action="/web/account-transaction-history" name="createForm" id="createForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="search"/>
                         <input type="hidden" name="account" id="account" value="${actionBean.account.id}"/>
                            <table width="100%">
                                <tr>
                                    <td>
                                        <c:if test="${!empty actionBean.accountEvents}">
                                            <strong>${actionBean.accountEventsSize}
                                            <fmt:message key="accounteventsfound"/> for ${actionBean.account.accountName}
                                            <fmt:message key="balance"/>: ${actionBean.account.availableBalance.amount}
                                            </strong>
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
                                    <td>
                                            <fmt:message key="typelabel"/>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="transactionType" id="transactionType" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selecttransactiontypelabel"/></s:option>
                                                   <c:forEach items="${actionBean.transactionTypes}" var="transactionType"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.transactionType !=null &&
                                                             actionBean.transactionType == transactionType}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${transactionType}" ${selectedValue}/>
                                                      ${transactionType}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                    </td>
                                    <td align="right" valign="bottom">
                                        <fmt:message key="searchnowlabel" var="searchnowlabel"/>
                                        <d:submit class="small" name="search" value="${searchnowlabel}"/>
                                    </td>
                                </tr>
                            </table>
                         </s:form>
                        <table class="alternating">
                            <c:if test="${!empty actionBean.accountEvents}">
                                <thead>
                                <tr>
                                    <td>
                                        <fmt:message key="datelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="desclabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="typelabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td>
                                        <fmt:message key="balanceafterlabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${actionBean.accountEvents}" var="accountEvent"
                                           varStatus="loopStatus">
                                    <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                                        <td>
                                            ${accountEvent.formattedDateCreated}
                                        </td>
                                        <td>
                                            ${accountEvent.title}
                                        </td>
                                        <td>
                                            ${accountEvent.transactionType}
                                        </td>
                                        <td>
                                           ${accountEvent.currency} ${accountEvent.amount}
                                        </td>
                                        <td>
                                           ${accountEvent.currency} ${accountEvent.availableBalance}
                                        </td>
                                    </tr>
                                </c:forEach>
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

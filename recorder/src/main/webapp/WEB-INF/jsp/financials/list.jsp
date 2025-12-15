<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Financials">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper" style="background-color: #fdfdfd; border-radius: 10px 10px 10px 10px;overflow:hidden;">
            <div class="container">
                <div id="content">
                    <table class="alternating">
                            <s:errors/>
                            <tbody>
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
                            </tbody>
                    </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>


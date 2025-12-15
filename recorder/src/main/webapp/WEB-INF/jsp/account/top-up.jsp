<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Top Up Account">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <!-- Main -->
        <div id="main-wrapper">
            <div class="container">
                <div class="row 200%">
                    <div class="8u important(collapse)">
                        <div id="content">
                            <div class="row">
                                <section class="6u 12u(narrower)">
                                    <div class="box post">
                                        <div class="inner">
                                            <s:errors/>
                                        </div>
                                    </div>
                                </section>
                            </div>
                            <!-- Content -->

                            <security:protected-element name="top-up-users">
                            <article>
                                <s:form action="/web/top-up" method="post" name="searchForm" id="searchForm">
                                <input type="hidden" name="target" value="${actionBean.target}"/>
                                <input type="hidden" name="_eventName" value="save"/>
                                <input type="hidden" name="user" value="${actionBean.user.id}"/>

                                <table>
                                    <thead>
                                    <tr>
                                        <th colspan="2">
                                            <h4><fmt:message key="topupaccountlabel"/> for ${actionBean.user.fullName}.
                                            <fmt:message key="balance"/> ${actionBean.user.account.availableBalance.amount}
                                            </h4>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                     <tr valign="top">
                                         <td nowrap="nowrap"><fmt:message key="amountlabel"/> (in USD)
                                          </td>
                                         <td nowrap="nowrap">
                                             <d:text style="background-color:#F0E68C" name="amount"
                                             id="amount"/>
                                         </td>
                                     </tr>
                                     <tr valign="top">
                                         <td nowrap="nowrap"><fmt:message key="passwordlabel"/>
                                          </td>
                                         <td nowrap="nowrap">
                                             <d:text style="background-color:#F0E68C" name="password"
                                             id="password"/>
                                         </td>
                                     </tr>
                                    <tr>
                                        <td>&nbsp;</td>
                                        <td align="right">
                                            <fmt:message key="submitlabel" var="submitlabel"/>
                                            <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                </s:form>
                              </article>
                           </security:protected-element>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

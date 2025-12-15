<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Vendor Details">

    <s:layout-component name="head">
     </s:layout-component>

    <s:layout-component name="contents">
<security:protected-element name="show-user-list">
        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                        <table class="alternating">
                            <thead>
                                <tr>
                                    <th colspan="2">
                                        <u><fmt:message key="orgdetailsheaderlabel"/></u>
                                    </th>
                                </tr>
                            </thead>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="namelabel"/>
                                </td>
                                <td>
                                    ${actionBean.org.name}
                                </td>
                            </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="balance"/>
                                  </td>
                                  <td>
                                      ${actionBean.org.account.availableBalance.amount}
                                  </td>
                              </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="addresslabel"/>
                                </td>
                                <td>
                                    ${actionBean.org.address.address}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="citylabel"/>
                                </td>
                                <td>
                                    ${actionBean.org.address.city}
                                </td>
                            </tr>
                            <tr>
                                <td class="strong">
                                    <fmt:message key="countrylabel"/>
                                </td>
                                <td>
                                        ${actionBean.org.address.country}
                                </td>
                            </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="emaillabel"/>
                                  </td>
                                  <td>
                                      <a href="mailto:${actionBean.org.email}">${actionBean.org.email}</a>
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="contactspersonlabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.org.contactPerson}
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="contactnumberslabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.org.phoneNumbers}
                                  </td>
                              </tr>
                              <tr>
                                  <td class="strong">
                                     <fmt:message key="taxidlabel"/>
                                  </td>
                                  <td>
                                      ${actionBean.org.taxId}
                                  </td>
                              </tr>
                            <tr>
                                <td align="right">
                                <c:if test="${actionBean.org.orgType == 'MERCHANT'}">
                                     <security:protected-element name="manage-merchants">
                                                          <d:link
                                                              href="/web/merchant/${actionBean.org.id}/disable">
                                                              <fmt:message key="disablelabel"/>
                                                          </d:link>
                                     </security:protected-element>
                                </c:if>
                                <c:if test="${actionBean.org.orgType != 'MERCHANT'}">
                                     <security:protected-element name="manage-vendors">
                                                          <d:link
                                                              href="/web/vendor/${actionBean.org.id}/disable">
                                                              <fmt:message key="disablelabel"/>
                                                          </d:link>
                                     </security:protected-element>
                                     <security:protected-element name="manage-vendor-agents">
                                                          | <d:link
                                                                     href="/web/vendor-agents?vendor=${actionBean.org.id}&agentStatus=ACTIVE">
                                                                     <fmt:message key="agentslabel"/>(${actionBean.org.numAgents})
                                                          </d:link>
                                     </security:protected-element>
                                </c:if>
                            </tbody>
                        </table>
                </div>
            </div>
        </div>
        </security:protected-element>
    </s:layout-component>
</s:layout-render>

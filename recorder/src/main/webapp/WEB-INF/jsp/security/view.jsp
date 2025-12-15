<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Security">

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
                                            <d:link
                                               href="/web/security?type=ROLES">
                                               <fmt:message key="viewroleslabel"/>
                                            </d:link>
                                          | <d:link
                                              href="/web/security?type=FEATURES">
                                              <fmt:message key="featureslabel"/>
                                           </d:link>
                                           | <d:link
                                               href="/web/security?type=POLICIES">
                                               <fmt:message key="securitypolicieslabel"/>
                                            </d:link>
                                            | <d:link
                                               href="/web/security?type=POLICIES&_eventName=ascii"  target="_blank">
                                               <fmt:message key="securitypoliciesasimagelabel"/>
                                            </d:link>
                                </td>
                                </tr>
                                 <tr>
                                    <td>
                                      <security:protected-element name="view-security-policies">
                                          ${actionBean.content}
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


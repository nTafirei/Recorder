<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Select User Type">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                        <table class="alternating">
                                <thead>
                                <tr>
                                    <td>
                                                 <d:link
                                                             href="/web/register?regType=USER">
                                                             <fmt:message key="asuserlabel"/>
                                                   </d:link>
                                    </td>
                                    <td>|</td>
                                    <td>
                                                 <d:link
                                                             href="/web/register?regType=AGENT">
                                                             <fmt:message key="asagentlabel"/>
                                                   </d:link>
                                    </td>
                                </tr>
                                </thead>
                        </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

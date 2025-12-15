<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Verification Pending">

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
                                         <fmt:message key="registrationstatuslabel"/>
                                    </td>
                                </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <fmt:message key="approachagent"/>
                                        </td>
                                    </tr>
                                </tbody>
                        </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

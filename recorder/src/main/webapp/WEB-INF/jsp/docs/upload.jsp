<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Upload Recording">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <s:errors/>
                    <strong><fmt:message key="uploadrecording"/>
                    </strong>
                        <s:form action="/web/upload" method="post" name="searchForm" id="searchForm"
                        enctype = "multipart/form-data">
                        <input type="hidden" name="_eventName" value="upload"/>
                        <table class="alternating">
                                <tr>
                                    <td>
                                        File
                                    </td>
                                    <td align="right">
                                        <input type="file" name="fileBean" id="fileBean" style="background-color:#F0E68C"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="actionlabel"/>
                                    </td>
                                        <td align="right">
                                            <fmt:message key="submitlabel" var="submitlabel"/>
                                            <d:submit name="submit" class="small" value="${submitlabel}" id="login"/>
                                        </td>
                                    </tr>
                        </table>
                      </s:form>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

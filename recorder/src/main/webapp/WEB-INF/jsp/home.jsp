<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Home">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper" style="background-color: #fdfdfd; border-radius: 10px 10px 10px 10px;overflow:hidden;">
            <div class="container">
                <div id="content">
                    <strong>What is the Recorder platform?</strong><br/>
                    <br>Recorder is a corruption reduction tool. Simple! <br/>
                    The Recorder platform allows you to record your conversations using our mobile app discreetly.
                    <br/>
                    The conversation is automatically saved in our computers in the cloud without further action from you.
                    <br/>
                    The intention is to allow people to record fraud, bribes ad extortion. The recordings can then be
                    used in reporting to ZACC, the police as well as be used in a court of law.
                    <br/>
                    We use Artificial Intelligence (AI) to identify the parties involved.<br/>
                    However, you should always ask for the officer's name and identification (this is allowed by law).
                    <br/>
                    Always make sure you capture the officer's name or identification verbally so that the app can pick
                    it up and record it for later use. Do this at the beginning of the conversation.

<br/><br/>
                    <strong>Scenario:</strong><br/>
                    You are driving. You get stopped by police. You immediately start the Recorder Mobile App
                    to capture the conversation.
                    <br/>
                    The police officer ask for a bribe. You can pay it and later report the matter to ZACC using
                    Recorder as evidence, or, you can refuse to pay and inform the officer that he/she has been
                    recorded and will be reported to ZACC.
                    <br/>
                    You can even play the recording to the officer. Even if they delete the recording from your device,
                    it does noy matter since it is automatically uploaded to our computers in the cloud.

<br/><br/>
                    <strong>Benefits:</strong><br/>
                    You protect yourself from bribes and extortion by the police and civil servants.
                </div>
            </div>
        </div>
        <div>
            <s:errors/>
        </div>
    </s:layout-component>
</s:layout-render>


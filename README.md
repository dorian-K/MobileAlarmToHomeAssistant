Mobile Alarm -> Home Assistant
==============================

This android app listens for a Notification from the android alarm clock until an alarm clock rings, at which point it will send a request to a user-supplied webhook

This works via the HomeAssistant template feature, which sets up a "dummy" sensor which can be altered with various triggers, including a webhook.
BEGIN WORK;
UPDATE `contents` SET typeId='events.meeting' WHERE typeId='meets.meet';
UPDATE `containers` SET typeId='events.root', toolName='events', name='events' WHERE typeId='meets.root';
UPDATE `groups_tool_configurations` SET mapkey='events' WHERE mapkey='meets';
COMMIT;
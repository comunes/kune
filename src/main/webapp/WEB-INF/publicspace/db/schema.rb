# This file is auto-generated from the current state of the database. Instead of editing this file, 
# please use the migrations feature of Active Record to incrementally modify your database, and
# then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your database schema. If you need
# to create the application database on another system, you should be using db:schema:load, not running
# all the migrations from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 0) do

  create_table "DATABASECHANGELOG", :id => false, :force => true do |t|
    t.string   "ID",           :limit => 63,  :null => false
    t.string   "AUTHOR",       :limit => 63,  :null => false
    t.string   "FILENAME",     :limit => 200, :null => false
    t.datetime "DATEEXECUTED",                :null => false
    t.string   "MD5SUM",       :limit => 32
    t.string   "DESCRIPTION"
    t.string   "COMMENTS"
    t.string   "TAG"
    t.string   "LIQUIBASE",    :limit => 10
  end

  create_table "DATABASECHANGELOGLOCK", :primary_key => "ID", :force => true do |t|
    t.boolean  "LOCKED",      :null => false
    t.datetime "LOCKGRANTED"
    t.string   "LOCKEDBY"
  end

  create_table "access_lists", :force => true do |t|
    t.integer "admins_id",  :limit => 8
    t.integer "viewers_id", :limit => 8
    t.integer "editors_id", :limit => 8
  end

  add_index "access_lists", ["admins_id"], :name => "FK8BFAE0FA363215C7"
  add_index "access_lists", ["editors_id"], :name => "FK8BFAE0FAD81FF4E5"
  add_index "access_lists", ["viewers_id"], :name => "FK8BFAE0FAE4742B0A"

  create_table "comment", :force => true do |t|
    t.integer "publishedOn", :limit => 8, :null => false
    t.string  "text"
    t.integer "parent_id",   :limit => 8
    t.integer "content_id",  :limit => 8
    t.integer "author_id",   :limit => 8
  end

  add_index "comment", ["author_id"], :name => "FK38A5EE5FFC0AA1C6"
  add_index "comment", ["content_id"], :name => "FK38A5EE5F855E2DEE"
  add_index "comment", ["parent_id"], :name => "FK38A5EE5FF0EEDA03"

  create_table "comment_kusers", :id => false, :force => true do |t|
    t.integer "comment_id",        :limit => 8, :null => false
    t.integer "positiveVoters_id", :limit => 8, :null => false
    t.integer "negativeVoters_id", :limit => 8, :null => false
    t.integer "abuseInformers_id", :limit => 8, :null => false
  end

  add_index "comment_kusers", ["abuseInformers_id"], :name => "FKF6AF0E5D6C19357A"
  add_index "comment_kusers", ["comment_id"], :name => "FKF6AF0E5DAB201C2E"
  add_index "comment_kusers", ["negativeVoters_id"], :name => "FKF6AF0E5D52E7C011"
  add_index "comment_kusers", ["positiveVoters_id"], :name => "FKF6AF0E5D9256ECD"

  create_table "container_translation", :force => true do |t|
    t.string  "name"
    t.integer "language_id", :limit => 8
  end

  add_index "container_translation", ["language_id"], :name => "FK312BE3F3DE7DCA4"

  create_table "containers", :force => true do |t|
    t.string  "name"
    t.string  "toolName"
    t.string  "typeId"
    t.integer "parent_id",      :limit => 8
    t.integer "accessLists_id", :limit => 8
    t.integer "language_id",    :limit => 8
    t.integer "owner_id",       :limit => 8
  end

  add_index "containers", ["accessLists_id"], :name => "FK8A844122BE828CE"
  add_index "containers", ["language_id"], :name => "FK8A84412DE7DCA4"
  add_index "containers", ["owner_id"], :name => "FK8A84412411D747A"
  add_index "containers", ["parent_id"], :name => "FK8A84412F026D325"

  create_table "containers_container_translation", :primary_key => "containerTranslations_id", :force => true do |t|
    t.integer "containers_id", :limit => 8, :null => false
  end

  add_index "containers_container_translation", ["containerTranslations_id"], :name => "FK1BA62F86CF990D13"
  add_index "containers_container_translation", ["containerTranslations_id"], :name => "containerTranslations_id", :unique => true
  add_index "containers_container_translation", ["containers_id"], :name => "FK1BA62F86EA0AFEBD"

  create_table "containers_containers", :id => false, :force => true do |t|
    t.integer "containers_id",   :limit => 8, :null => false
    t.integer "absolutePath_id", :limit => 8, :null => false
  end

  add_index "containers_containers", ["absolutePath_id"], :name => "FK8249765FE465B073"
  add_index "containers_containers", ["containers_id"], :name => "FK8249765FEA0AFEBD"

  create_table "content_translations", :force => true do |t|
    t.integer "contentId",   :limit => 8
    t.integer "language_id", :limit => 8
  end

  add_index "content_translations", ["language_id"], :name => "FKF10565E8DE7DCA4"

  create_table "contents", :force => true do |t|
    t.integer  "createdOn",       :limit => 8, :null => false
    t.datetime "deletedOn"
    t.string   "filename"
    t.string   "subtype"
    t.string   "type"
    t.datetime "publishedOn"
    t.string   "status",                       :null => false
    t.string   "typeId"
    t.integer  "version",                      :null => false
    t.integer  "language_id",     :limit => 8, :null => false
    t.integer  "lastRevision_id", :limit => 8
    t.integer  "container_id",    :limit => 8
    t.integer  "license_id",      :limit => 8
    t.integer  "accessLists_id",  :limit => 8
  end

  add_index "contents", ["accessLists_id"], :name => "FKDE2F5B1A2BE828CE"
  add_index "contents", ["container_id"], :name => "FKDE2F5B1AF4676BEE"
  add_index "contents", ["language_id"], :name => "FKDE2F5B1ADE7DCA4"
  add_index "contents", ["lastRevision_id"], :name => "FKDE2F5B1A8D6FD330"
  add_index "contents", ["license_id"], :name => "FKDE2F5B1AF302B8EE"

  create_table "contents_content_translations", :primary_key => "translations_id", :force => true do |t|
    t.integer "contents_id", :limit => 8, :null => false
  end

  add_index "contents_content_translations", ["contents_id"], :name => "FKF5E4F9ED5E7577AD"
  add_index "contents_content_translations", ["translations_id"], :name => "FKF5E4F9ED2F9273BC"
  add_index "contents_content_translations", ["translations_id"], :name => "translations_id", :unique => true

  create_table "contents_kusers", :id => false, :force => true do |t|
    t.integer "contents_id", :limit => 8, :null => false
    t.integer "authors_id",  :limit => 8, :null => false
  end

  add_index "contents_kusers", ["authors_id"], :name => "FK956FE9424AA7DDE9"
  add_index "contents_kusers", ["contents_id"], :name => "FK956FE9425E7577AD"

  create_table "contents_tags", :id => false, :force => true do |t|
    t.integer "contents_id", :limit => 8, :null => false
    t.integer "tags_id",     :limit => 8, :null => false
  end

  add_index "contents_tags", ["contents_id"], :name => "FK2200721E5E7577AD"
  add_index "contents_tags", ["tags_id"], :name => "FK2200721E66F49F6F"

  create_table "customproperties", :force => true do |t|
    t.binary "data"
  end

  create_table "globalize_countries", :force => true do |t|
    t.string "code",                   :limit => 2
    t.string "currency_code",          :limit => 3
    t.string "currency_decimal_sep",   :limit => 2
    t.string "currency_format"
    t.string "date_format"
    t.string "decimal_sep",            :limit => 2
    t.string "english_name"
    t.string "number_grouping_scheme"
    t.string "thousands_sep",          :limit => 2
  end

  add_index "globalize_countries", ["id"], :name => "id", :unique => true

# Could not dump table "globalize_languages" because of following StandardError
#   Unknown type 'bit(1)' for column 'macro_language'

  create_table "globalize_translations", :force => true do |t|
    t.string  "facet"
    t.integer "item_id"
    t.integer "pluralization_index"
    t.string  "table_name"
    t.string  "text"
    t.string  "tr_key"
    t.string  "type"
    t.integer "language_id",         :limit => 8
  end

  add_index "globalize_translations", ["id"], :name => "id", :unique => true
  add_index "globalize_translations", ["language_id"], :name => "FKCB245A90DE7DCA4"

  create_table "group_list", :force => true do |t|
    t.integer "mode"
  end

  create_table "group_list_groups", :id => false, :force => true do |t|
    t.integer "group_list_id", :limit => 8, :null => false
    t.integer "list_id",       :limit => 8, :null => false
  end

  add_index "group_list_groups", ["group_list_id"], :name => "FK531B66D542BCDC2D"
  add_index "group_list_groups", ["list_id"], :name => "FK531B66D5E8D46AAF"

  create_table "groups", :force => true do |t|
    t.string  "admissionType",                   :null => false
    t.string  "groupType",                       :null => false
    t.binary  "logo"
    t.string  "subtype"
    t.string  "type"
    t.string  "longName",          :limit => 50
    t.string  "shortName",         :limit => 15
    t.string  "workspaceTheme"
    t.integer "defaultLicense_id", :limit => 8
    t.integer "socialNetwork_id",  :limit => 8
    t.integer "defaultContent_id", :limit => 8
    t.integer "groupFullLogo_id",  :limit => 8
  end

  add_index "groups", ["defaultContent_id"], :name => "FKB63DD9D4212FE6EF"
  add_index "groups", ["defaultLicense_id"], :name => "FKB63DD9D48ED471EF"
  add_index "groups", ["groupFullLogo_id"], :name => "FKB63DD9D4F6BBBA2E"
  add_index "groups", ["longName"], :name => "longName", :unique => true
  add_index "groups", ["shortName"], :name => "shortName", :unique => true
  add_index "groups", ["socialNetwork_id"], :name => "FKB63DD9D4AC6815CE"

  create_table "groups_tool_configurations", :id => false, :force => true do |t|
    t.integer "groups_id",      :limit => 8,                 :null => false
    t.integer "toolsConfig_id", :limit => 8,                 :null => false
    t.string  "mapkey",                      :default => "", :null => false
  end

  add_index "groups_tool_configurations", ["groups_id"], :name => "FK1CDF00D985A52DB9"
  add_index "groups_tool_configurations", ["toolsConfig_id"], :name => "FK1CDF00D9DCE07FCF"
  add_index "groups_tool_configurations", ["toolsConfig_id"], :name => "toolsConfig_id", :unique => true

  create_table "kusers", :force => true do |t|
    t.integer "buddiesVisibility"
    t.string  "email",                             :null => false
    t.string  "name",                :limit => 50, :null => false
    t.string  "password",            :limit => 40, :null => false
    t.string  "shortName",           :limit => 15
    t.string  "timezone",                          :null => false
    t.integer "customProperties_id", :limit => 8
    t.integer "country_id",          :limit => 8,  :null => false
    t.integer "language_id",         :limit => 8,  :null => false
    t.integer "userGroup_id",        :limit => 8
  end

  add_index "kusers", ["country_id"], :name => "FKBD3D187D7E2112D0"
  add_index "kusers", ["customProperties_id"], :name => "FKBD3D187D7F551706"
  add_index "kusers", ["email"], :name => "email", :unique => true
  add_index "kusers", ["language_id"], :name => "FKBD3D187DDE7DCA4"
  add_index "kusers", ["shortName"], :name => "shortName", :unique => true
  add_index "kusers", ["userGroup_id"], :name => "FKBD3D187DCA8A3DF9"

# Could not dump table "licenses" because of following StandardError
#   Unknown type 'bit(1)' for column 'isCC'

  create_table "rates", :force => true do |t|
    t.float   "value"
    t.integer "content_id", :limit => 8
    t.integer "rater_id",   :limit => 8
  end

  add_index "rates", ["content_id", "rater_id"], :name => "content_id", :unique => true
  add_index "rates", ["content_id"], :name => "FK6744F93855E2DEE"
  add_index "rates", ["rater_id"], :name => "FK6744F93BD4FE25F"

  create_table "revisions", :force => true do |t|
    t.text    "body",        :limit => 2147483647
    t.integer "createdOn",   :limit => 8,          :null => false
    t.string  "title"
    t.integer "version",                           :null => false
    t.integer "editor_id",   :limit => 8
    t.integer "previous_id", :limit => 8
    t.integer "content_id",  :limit => 8
  end

  add_index "revisions", ["content_id"], :name => "FK1E2243F8855E2DEE"
  add_index "revisions", ["editor_id"], :name => "FK1E2243F835BDFEA4"
  add_index "revisions", ["previous_id"], :name => "FK1E2243F813E596EA"

  create_table "social_networks", :force => true do |t|
    t.integer "visibility"
    t.integer "pendingCollaborators_id", :limit => 8
    t.integer "accessLists_id",          :limit => 8
  end

  add_index "social_networks", ["accessLists_id"], :name => "FK7E9610972BE828CE"
  add_index "social_networks", ["pendingCollaborators_id"], :name => "FK7E961097F7434A95"

  create_table "tags", :force => true do |t|
    t.string "name"
  end

  add_index "tags", ["name"], :name => "name", :unique => true

# Could not dump table "tool_configurations" because of following StandardError
#   Unknown type 'bit(1)' for column 'enabled'

end

class Content < ActiveRecord::Base
  belongs_to :last_revision, :class_name => 'Revision', :foreign_key => 'lastRevision_id'
end

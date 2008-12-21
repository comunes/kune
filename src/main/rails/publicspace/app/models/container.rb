class Container < ActiveRecord::Base
  has_many :contents
  belongs_to :owner, :class_name => 'Group', :foreign_key => 'owner_id'
end

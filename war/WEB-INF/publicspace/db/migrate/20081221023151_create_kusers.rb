class CreateKusers < ActiveRecord::Migration
  def self.up
    create_table :kusers do |t|

      t.timestamps
    end
  end

  def self.down
    drop_table :kusers
  end
end

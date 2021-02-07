class AddKeyToResultFile < ActiveRecord::Migration
  def self.up
    add_column :result_files, :team_key_id, :integer
  end

  def self.down
    remove_column :result_files, :team_key_id
  end
end

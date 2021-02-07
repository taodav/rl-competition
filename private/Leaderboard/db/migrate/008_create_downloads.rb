class CreateDownloads < ActiveRecord::Migration
  def self.up
    create_table :downloads do |t|
      t.column :team_id, :integer
      t.column :revision, :integer
    end
  end

  def self.down
    drop_table :downloads
  end
end

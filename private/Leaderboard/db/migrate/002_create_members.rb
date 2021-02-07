class CreateMembers < ActiveRecord::Migration
  def self.up
    create_table :members do |t|
      t.column :name,         :string
      t.column :institution,  :string
      t.column :affiliation,  :string
      t.column :email,        :string
      t.column :country,      :string
      t.column :team_id,      :integer
      t.column :is_leader,    :boolean
    end
  end

  def self.down
    drop_table :members
  end
end

create or replace FUNCTION rrtest.quote_xml 
(
  input_srt in nclob
)
    RETURN nclob IS
begin
  return 
    replace(replace(replace(replace(replace(input_srt
      , '&', '&'||'amp;')
      , '<', '&'||'lt;')
      , '>', '&'||'gt;')
      , '"', '&'||'quot;')
      , '''', '&'||'apos;');
end;
/
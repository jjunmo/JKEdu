import styled from "styled-components";

const Term = styled.div`
  border: 2px solid grey;
  border-radius: 10px;
  height: 150px;
  color: black;
  margin: 5px 0 0 0;
  overflow: auto;
`;

const TermOfCollectInfoBox = () => {
  return (
    <Term>
      <h3>개인정보수집 동의</h3>
      <span>
        개인정보보호법에 따라 제이케이에듀에 회원가입 신청하시는 분께 수집하는
        개인정보의 항목 ~~~
      </span>
    </Term>
  );
};

export default TermOfCollectInfoBox;
